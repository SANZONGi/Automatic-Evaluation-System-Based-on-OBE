package com.sanzong.obe.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class Permission  {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名称
     */
    private String permission;


}
