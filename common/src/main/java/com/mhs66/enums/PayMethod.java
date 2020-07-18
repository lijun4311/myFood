package com.mhs66.enums;

/**
 * @Description: 支付方式 枚举
 */
public enum PayMethod {
	/**
	 *支付方式 枚举
	 */
	WEIXIN(1, "微信"),
	ALIPAY(2, "支付宝");

	public final Integer type;
	public final String value;

	PayMethod(Integer type, String value){
		this.type = type;
		this.value = value;
	}

}
