package com.sanzong.obe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzong.obe.entity.Assignment;
import com.sanzong.obe.mapper.AssignmentMapper;
import com.sanzong.obe.service.IAssignmentService;
import org.springframework.stereotype.Service;

/**
 * @author SANZONG
 */
@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment> implements IAssignmentService {
}
