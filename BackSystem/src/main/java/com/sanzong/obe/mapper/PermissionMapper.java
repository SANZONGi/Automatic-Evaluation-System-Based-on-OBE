package com.sanzong.obe.mapper;

import com.sanzong.obe.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 获取角色权限
     *
     * @param roleId 用户id
     * @return 权限列表
     */
    List<Integer> getByRoleId(List<Integer> roleId);
}
