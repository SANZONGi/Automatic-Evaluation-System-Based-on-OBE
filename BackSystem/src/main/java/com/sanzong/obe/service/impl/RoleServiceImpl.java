package com.sanzong.obe.service.impl;

import com.sanzong.obe.entity.Role;
import com.sanzong.obe.mapper.RoleMapper;
import com.sanzong.obe.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Integer> getByUserId(int userId) {
        return roleMapper.getByUserId(userId);
    }
}
