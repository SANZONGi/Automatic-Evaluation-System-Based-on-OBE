package com.sanzong.obe.mapper;

import com.sanzong.obe.entity.CurriculumObj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanzong.obe.entity.model.AssignmentToCurriculumObjModel;
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
public interface CurriculumObjMapper extends BaseMapper<CurriculumObj> {
     /**
      * 获取连接关系
      * @return
      */
     List<AssignmentToCurriculumObjModel> getCurriculumObjWithAssignment();
}
