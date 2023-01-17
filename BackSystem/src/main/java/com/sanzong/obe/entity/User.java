package com.sanzong.obe.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class User  {


    public User(String account, String passwd, String salt) {
        this.account = account;
        this.passwd = passwd;
        this.salt = salt;
    }

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号（学号/工号）
     */
    private String account;

    /**
     * 密码
     */
    private String passwd;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 盐
     */
    private String salt;
}
