package com.mhs66.enums;


import com.mhs66.config.ILogBase;

/**
 *description:
 * 返回类型枚举
 *@author 76442
 *@date 2020-07-15 20:33
 */
public enum WebResult {
    //请求成功
    SUCCESS(200, "SUCCESS"),
    //请求错误
    ERROR(500, "ERROR"),
    //未登录
    NOLOGIN(800, "NOLOGIN"),
    //参数不合法
    ILLEGAL_PARAM(900, "ILLEGAL_PARAM");

    private final int code;
    private final String desc;


    WebResult(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String codeOf(int code) {
        for (WebResult restEnum : values()) {
            if (restEnum.getCode() == code) {
                return restEnum.desc;
            }
        }
        ILogBase.log.error("RestEnum 错误码描述获取异常 {}", code);
        return "错误码描述获取异常";
    }

}
