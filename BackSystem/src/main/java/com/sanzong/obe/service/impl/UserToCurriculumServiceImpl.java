package com.sanzong.obe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzong.obe.entity.model.UserToCurriculumModel;
import com.sanzong.obe.mapper.UserToCurriculumMapper;
import com.sanzong.obe.service.IUserToCurriculumService;
import org.springframework.stereotype.Service;

@Service
public class UserToCurriculumServiceImpl extends ServiceImpl<UserToCurriculumMapper, UserToCurriculumModel> implements IUserToCurriculumService {
}
