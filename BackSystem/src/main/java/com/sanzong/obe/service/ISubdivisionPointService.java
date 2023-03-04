package com.sanzong.obe.service;

import com.alibaba.fastjson2.JSONArray;
import com.sanzong.obe.entity.SubdivisionPoint;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
public interface ISubdivisionPointService extends IService<SubdivisionPoint> {

    JSONArray getMatrix(String gradReqId);
}
