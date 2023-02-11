package com.sanzong.obe.controller;


import com.sanzong.obe.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@RestController
@RequestMapping("/obe/role")
public class RoleController {
    @Autowired
    IRoleService roleService;

    public List<Integer> getRoleByUserId(int userId) {
        return roleService.getByUserId(userId);
    }
}
