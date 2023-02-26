package com.sanzong.obe.entity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SANZONG
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("assignment_to_curriculum_obj")
public class AssignmentToCurriculumObjModel {
    private int assignmentId;

    private int curriculumObjId;

    private int weight;
}
