package com.mhs66.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 用户表 
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Users extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户名 用户名
     */
    private String username;

    /**
     * 密码 密码
     */
    private String password;

    /**
     * 昵称 昵称
     */
    private String nickname;

    /**
     * 真实姓名 真实姓名
     */
    private String realname;

    /**
     * 头像 头像
     */
    private String face;

    /**
     * 手机号 手机号
     */
    private String mobile;

    /**
     * 邮箱地址 邮箱地址
     */
    private String email;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    private Integer sex;

    /**
     * 生日 生日
     */
    private LocalDate birthday;




}
