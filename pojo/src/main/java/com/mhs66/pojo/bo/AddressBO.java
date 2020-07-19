package com.mhs66.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户新增或修改地址的BO
 */

@Data
public class AddressBO {

    private String addressId;
    @NotBlank(message = "用户id不能为空")
    private String userId;
    @Length(max = 12)
    private String receiver;
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\\\d{8}$", message = "手机格式不正确")
    private String mobile;
    @NotBlank(message = "收货地址信息不能为空")
    private String province;
    @NotBlank(message = "收货地址信息不能为空")
    private String city;
    @NotBlank(message = "收货地址信息不能为空")
    private String district;
    @NotBlank(message = "收货地址信息不能为空")
    private String detail;


}
