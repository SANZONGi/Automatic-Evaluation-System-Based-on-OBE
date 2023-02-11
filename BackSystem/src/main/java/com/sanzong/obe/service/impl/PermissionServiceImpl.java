package com.sanzong.obe.service.impl;

import com.sanzong.obe.entity.Permission;
import com.sanzong.obe.mapper.PermissionMapper;
import com.sanzong.obe.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public List<Integer> getByRoleId(List<Integer> roleId) {
        List<Integer> permissions = permissionMapper.getByRoleId(roleId).stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        return permissions;
    }
}
