package com.sanzong.obe.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanzong.obe.entity.User;
import com.sanzong.obe.service.impl.UserServiceImpl;
import com.sanzong.obe.utils.ResponseBody;
import com.sanzong.obe.utils.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@RestController
@RequestMapping("/obe/user")
public class UserController{
    @Autowired
    UserServiceImpl userService;


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
                return new ResponseBody("success" , null);
            }
        }

        return new ResponseBody("fail", null, "请检查输入");
    }

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
}
