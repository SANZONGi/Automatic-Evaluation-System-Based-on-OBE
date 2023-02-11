package com.sanzong.obe.interceptor;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanzong.obe.service.IPermissionService;
import com.sanzong.obe.service.IRoleService;
import com.sanzong.obe.utils.JwtUtils;
import com.sanzong.obe.utils.annotations.LoginRequired;
import com.sanzong.obe.utils.annotations.PermissionRequired;
import io.jsonwebtoken.SignatureException;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SANZONG
 */
@Component
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    IRoleService roleService;
    @Autowired
    IPermissionService permissionService;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否映射到方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Method method = ((HandlerMethod) handler).getMethod();
        PermissionRequired annotation = method.getAnnotation(PermissionRequired.class);

        if (annotation == null) {
            return true;
        }
        Map<String, Object> map = new HashMap<>(10);

        List<Integer> permissionsRequired = Arrays.stream(annotation.role()).boxed().collect(Collectors.toCollection(ArrayList::new));
        List<Integer> permissions = new JSONArray(jwtUtils.verify(request.getHeader("token")).getClaim("permissions").asString()).toList(Integer.TYPE);

        if (!new HashSet<>(permissions).containsAll(permissionsRequired)) {
            log.info("拥有权限 ====== {}", permissions);
            log.info("需要权限 ====== {}", permissionsRequired);
            map.put("state", 403);
            map.put("msg", "权限不足");
            String s = new ObjectMapper().writeValueAsString(map);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(s);
            return false;
        }
        return true;
    }
}
