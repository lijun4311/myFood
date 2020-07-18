package com.mhs66.pojo.bo;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * 用于创建订单的BO对象
 */
@Data
public class SubmitOrderBO {

    private String userId;
    private String itemSpecIds;
    private String addressId;
    /**
     * 正则1到2整数
     */
    @Pattern(regexp = "^[1-2]*$", message = "支付方式错误")
    private Integer payMethod;
    private String leftMsg;


}
