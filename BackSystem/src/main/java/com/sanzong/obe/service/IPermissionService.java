package com.sanzong.obe.service;

import com.sanzong.obe.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
public interface IPermissionService extends IService<Permission> {
    List<Integer> getByRoleId(List<Integer> roleId);
}
