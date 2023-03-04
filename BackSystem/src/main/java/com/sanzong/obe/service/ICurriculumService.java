package com.sanzong.obe.service;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sanzong.obe.entity.Curriculum;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
public interface ICurriculumService extends IService<Curriculum> {
    JSONArray getStudentAchievementByCurriculum(String curId);
}
