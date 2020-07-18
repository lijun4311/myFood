package com.mhs66;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Map;

/**
 * description:
 * 网络传输响应实体类
 * JsonInclude 保证序列化json的时候,如果是null的对象,key也会消失
 *
 * @author 76442
 * @date 2020-07-15 20:34
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebResult<T> implements Serializable {

    private final int status;
    private String msg;
    private T data;

    private WebResult(int status) {
        this.status = status;
    }

    private WebResult(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private WebResult(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private WebResult(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static <T> WebResult isSuccess(boolean success) {
        if (success) {
            return new WebResult(com.mhs66.enums.WebResult.SUCCESS.getCode());
        } else {
            return new WebResult(com.mhs66.enums.WebResult.ERROR.getCode(), com.mhs66.enums.WebResult.ERROR.getDesc());
        }
    }


    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }


    public static <T> WebResult ok() {
        return new WebResult(com.mhs66.enums.WebResult.SUCCESS.getCode());
    }

    public static <T> WebResult okMsg(String msg) {
        return new WebResult(com.mhs66.enums.WebResult.SUCCESS.getCode(), msg);
    }

    public static <T> WebResult okData(T data) {
        return new WebResult(com.mhs66.enums.WebResult.SUCCESS.getCode(), data);
    }

    public static <T> WebResult okDataMsg(String msg, T data) {
        return new WebResult(com.mhs66.enums.WebResult.SUCCESS.getCode(), msg, data);
    }


    public static <T> WebResult error() {
        return new WebResult(com.mhs66.enums.WebResult.ERROR.getCode(), com.mhs66.enums.WebResult.ERROR.getDesc());
    }


    public static <T> WebResult illegalParam() {
        return new WebResult(com.mhs66.enums.WebResult.ILLEGAL_PARAM.getCode(), com.mhs66.enums.WebResult.ILLEGAL_PARAM.getDesc());
    }

    public static WebResult illegalParamMap(Map<String, String> map) {
        return new WebResult(com.mhs66.enums.WebResult.ILLEGAL_PARAM.getCode(), com.mhs66.enums.WebResult.ILLEGAL_PARAM.getDesc(), map);
    }

    public static <T> WebResult noLgoin() {
        return new WebResult(com.mhs66.enums.WebResult.NOLOGIN.getCode(), com.mhs66.enums.WebResult.NOLOGIN.getDesc());
    }

    public static <T> WebResult errorMsg(String errorMessage) {
        return new WebResult(com.mhs66.enums.WebResult.ERROR.getCode(), errorMessage);
    }

    public static <T> WebResult errorDateMsg(String errorMessage, T data) {
        return new WebResult(com.mhs66.enums.WebResult.ERROR.getCode(), errorMessage, data);
    }

    public static <T> WebResult errorCodeMsg(int errorCode, String errorMessage) {
        return new WebResult(errorCode, errorMessage);
    }


}
