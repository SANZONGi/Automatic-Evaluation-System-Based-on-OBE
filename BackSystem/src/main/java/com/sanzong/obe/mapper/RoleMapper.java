package com.sanzong.obe.mapper;

import com.sanzong.obe.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 获取用户角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
   List<Integer> getByUserId(int userId);
}
