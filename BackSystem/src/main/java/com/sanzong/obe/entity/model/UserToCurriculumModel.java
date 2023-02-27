package com.sanzong.obe.entity.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author SANZONG
 */
@Data
@TableName("user_to_curriculum")
public class UserToCurriculumModel {
    @TableId
    private Integer id;

    private Integer userId;

    private Integer curriculumId;
}
