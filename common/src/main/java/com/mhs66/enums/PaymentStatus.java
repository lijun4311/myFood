package com.mhs66.enums;

/**
 * @author lijun
 * @date 2020-07-19 8:38
 * @description
 * @error
 * @since version-1.0
 */
public enum  PaymentStatus {
    /**
     *支付状态 10：未支付 20：已支付 30：支付失败 40：已退款
     */
    WAIT_PAY(10, "待付款"),
    WAIT_DELIVER(20, "已付款，待发货"),
    WAIT_RECEIVE(30, "已发货，待收货"),
    SUCCESS(40, "交易成功"),
    CLOSE(50, "交易关闭");

    public final Integer type;
    public final String value;

    PaymentStatus(Integer type, String value){
        this.type = type;
        this.value = value;
    }
}
