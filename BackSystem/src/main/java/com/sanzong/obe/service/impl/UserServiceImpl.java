package com.sanzong.obe.service.impl;

import com.sanzong.obe.entity.User;
import com.sanzong.obe.mapper.UserMapper;
import com.sanzong.obe.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
