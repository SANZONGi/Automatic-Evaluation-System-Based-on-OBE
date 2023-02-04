package com.sanzong.obe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Curriculum  {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名
     */
    private String name;

    /**
     * 教师姓名
     */
    private String teacher;

    /**
     * 开课学期
     */
    private String open;

    /**
     * 课程大纲
     */
    private String outline;

    /**
     * 考核标准
     */
    private String criterion;

    public Curriculum(Integer id, String name, String teacher, String open) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.open = open;
    }
}
