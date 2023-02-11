package com.sanzong.obe.service;

import com.sanzong.obe.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sanzong.obe.mapper.RoleMapper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
public interface IRoleService extends IService<Role> {
    List<Integer> getByUserId(int userId);
}
