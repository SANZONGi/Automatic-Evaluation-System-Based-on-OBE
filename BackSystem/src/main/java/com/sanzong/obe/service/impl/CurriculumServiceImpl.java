package com.sanzong.obe.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzong.obe.entity.Curriculum;
import com.sanzong.obe.entity.StudentModel;
import com.sanzong.obe.entity.model.UserToCurriculumModel;
import com.sanzong.obe.mapper.CurriculumMapper;
import com.sanzong.obe.mapper.UserToCurriculumMapper;
import com.sanzong.obe.service.ICurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

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
public class CurriculumServiceImpl extends ServiceImpl<CurriculumMapper, Curriculum> implements ICurriculumService {
    @Autowired
    private UserToCurriculumMapper userToCurriculumMapper;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public JSONArray getStudentAchievementByCurriculum(String curId) {
        List<UserToCurriculumModel> userToCurriculumModels = userToCurriculumMapper.selectList(new QueryWrapper<UserToCurriculumModel>()
                .eq("curriculum_id", curId));
        List<Integer> students = userToCurriculumModels.stream().map(UserToCurriculumModel::getUserId).collect(Collectors.toList());
        List<StudentModel> preStudents = mongoTemplate.findAll(StudentModel.class)
                .stream()
                .filter(item-> students.contains(getId(item.getName())))
                .collect(Collectors.toList());

        return null;
    }

    private Integer getId(String origin) {
        origin = origin.trim();
        int pos = 0;
        int id = 0;
        for (int i = origin.length() - 2; i >= 0; i--) {
            if (origin.charAt(i) == '(') {
                break;
            }
            id += (origin.charAt(i) - '0') * Math.pow(10, pos++);

        }
        return id;
    }
}
