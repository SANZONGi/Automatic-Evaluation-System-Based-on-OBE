package com.sanzong.obe.service;

import com.alibaba.fastjson2.JSONArray;
import com.sanzong.obe.entity.CurriculumObj;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
public interface ICurriculumObjService extends IService<CurriculumObj> {

    JSONArray getMatrix(int curriculumId);
}
