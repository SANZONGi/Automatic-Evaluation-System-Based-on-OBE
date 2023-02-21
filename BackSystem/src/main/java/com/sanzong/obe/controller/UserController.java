package com.sanzong.obe.controller;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanzong.obe.entity.User;
import com.sanzong.obe.service.IPermissionService;
import com.sanzong.obe.service.IRoleService;
import com.sanzong.obe.service.impl.UserServiceImpl;
import com.sanzong.obe.utils.JwtUtils;
import com.sanzong.obe.utils.ResponseBody;
import com.sanzong.obe.utils.StringUtils;
import com.sanzong.obe.utils.annotations.LoginRequired;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@RestController
@Slf4j
@RequestMapping("/obe/user")
public class UserController{
    @Autowired
    UserServiceImpl userService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    IRoleService roleService;
    @Autowired
    IPermissionService permissionService;

    @LoginRequired
    @PostMapping("/verify")
    public ResponseBody verifyUser(@RequestBody JSONObject jsonObject) {
        String account = jsonObject.getStr("account");
        String pwd = jsonObject.getStr("pwd");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        User user = userService.getOne(queryWrapper);


        if (null == user) {
            return new ResponseBody("fail", null, "请注册");
        }
        if (StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(user.getPasswd())) {
            if (user.getPasswd().equals(BCrypt.hashpw(pwd, user.getSalt()))) {
                JSON parse = JSONUtil.parse(permissionService.getByRoleId(roleService.getByUserId(user.getId())));
                String token = jwtUtils.generateToken(new HashMap<String, String>() {
                    {
                        put("uid", user.getId().toString());
                        put("permissions", parse.toStringPretty());
                    }
                });
                log.info("generate token ================= {}", token);
                user.setPasswd(null);
                user.setSalt(null);
                return new ResponseBody("success" , user , token);
            }
        }

        return new ResponseBody("fail", null, "请检查输入");
    }

    @LoginRequired
    @PostMapping("/register")
    public ResponseBody register(@RequestBody JSONObject jsonObject) {
        String account = jsonObject.getStr("account");
        String pwd = jsonObject.getStr("pwd");
        if (userService.count(new QueryWrapper<User>().eq("account", account)) > 0) {
            return new ResponseBody("fail", null, "重复注册");
        }

        String salt = BCrypt.gensalt();
        String hashedPwd = BCrypt.hashpw(pwd, salt);
        userService.save(new User(account, hashedPwd, salt));
        return new ResponseBody("success", null);
    }

    @LoginRequired
    @PostMapping("/editInf")
    public ResponseBody editUserInf(@RequestBody User user) {
        userService.updateById(user);
        return new ResponseBody("success", null);
    }

    @LoginRequired
    @PostMapping("/changePwd")
    public ResponseBody changePwd(@RequestBody JSONObject jsonObject) {
        ResponseBody verifyUserBody = verifyUser(jsonObject);
        if ("success".equals(verifyUserBody.getStatus())) {
            User user = (User) verifyUserBody.getData();
            String newPwd = jsonObject.getStr("newPwd");
            String salt = BCrypt.gensalt();
            String hashedPwd = BCrypt.hashpw(newPwd, salt);
            user.setSalt(salt);
            user.setPasswd(hashedPwd);
            userService.updateById(user);
        } else {
            return new ResponseBody("fail", null);
        }
        return new ResponseBody("success", null);
    }


}
