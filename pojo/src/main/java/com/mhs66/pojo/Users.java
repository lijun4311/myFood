package com.mhs66.pojo;

import com.mhs66.pojo.bo.UserBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
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
@EqualsAndHashCode(callSuper = true)
public class Users extends BaseEntity implements Serializable {



    private static final long serialVersionUID = 1L;


    /**
     * 用户名 用户名
     */
    @NotBlank(message = "用户名不能为空",groups = {UserBO.Login.class,UserBO.Regist.class})
    @ApiModelProperty(value = "用户名", example = "json")
    private String username;

    /**
     * 密码 密码
     */
    @NotBlank(message = "密码不能为空",groups = {UserBO.Login.class,UserBO.Regist.class})
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    /**
     * 昵称 昵称
     */

    @Length(max = 12, message = "用户昵称不能超过12位")
    @ApiModelProperty(value = "用户昵称", example = "杰森")
    private String nickname;

    /**
     * 真实姓名 真实姓名
     */
    @Length(max = 12, message = "用户真实姓名不能超过12位")
    @ApiModelProperty(value = "真实姓名", example = "杰森")
    private String realname;

    /**
     * 头像 头像
     */
    private String face;

    /**
     * 手机号 手机号
     */
    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$", message = "手机号格式不正确")
    @ApiModelProperty(value = "手机号", example = "13999999999")
    private String mobile;

    /**
     * 邮箱地址 邮箱地址
     */
    @Email
    @ApiModelProperty(value = "邮箱地址", example = "imooc@imooc.com")
    private String email;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 2, message = "性别选择不正确")
    @ApiModelProperty(value = "性别", example = "0:女 1:男 2:保密")
    private Integer sex;


    /**
     * 生日 生日
     */
    @ApiModelProperty(value = "生日", example = "1900-01-01")
    private LocalDate birthday;


}
