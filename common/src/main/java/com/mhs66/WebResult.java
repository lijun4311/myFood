package com.mhs66;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mhs66.enums.WebResultEnum;

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

    public static <T> WebResult<T> isSuccess(boolean success) {
        if (success) {
            return new WebResult<>(WebResultEnum.SUCCESS.getCode());
        } else {
            return new WebResult<>(WebResultEnum.ERROR.getCode(), WebResultEnum.ERROR.getDesc());
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


    public static <T> WebResult<T> ok() {
        return new WebResult<>(WebResultEnum.SUCCESS.getCode());
    }

    public static <T> WebResult<T> okMsg(String msg) {
        return new WebResult<>(WebResultEnum.SUCCESS.getCode(), msg);
    }

    public static <T> WebResult<T> okData(T data) {
        return new WebResult<>(WebResultEnum.SUCCESS.getCode(), data);
    }

    public static <T> WebResult<T> okDataMsg(String msg, T data) {
        return new WebResult<>(WebResultEnum.SUCCESS.getCode(), msg, data);
    }


    public static <T> WebResult<T> error() {
        return new WebResult<>(WebResultEnum.ERROR.getCode(), WebResultEnum.ERROR.getDesc());
    }


    public static <T> WebResult<T> illegalParam() {
        return new WebResult<>(WebResultEnum.ILLEGAL_PARAM.getCode(), WebResultEnum.ILLEGAL_PARAM.getDesc());
    }

    public static WebResult<Map<String, String>> illegalParamMap(Map<String, String> map) {
        return new WebResult<>(WebResultEnum.ILLEGAL_PARAM.getCode(), WebResultEnum.ILLEGAL_PARAM.getDesc(), map);
    }

    public static <T> WebResult<T> noLgoin() {
        return new WebResult<>(WebResultEnum.NOLOGIN.getCode(), WebResultEnum.NOLOGIN.getDesc());
    }

    public static <T> WebResult<T> errorMsg(String errorMessage) {
        return new WebResult<>(WebResultEnum.ERROR.getCode(), errorMessage);
    }

    public static <T> WebResult<T> errorDateMsg(String errorMessage, T data) {
        return new WebResult<>(WebResultEnum.ERROR.getCode(), errorMessage, data);
    }

    public static <T> WebResult<T> errorCodeMsg(int errorCode, String errorMessage) {
        return new WebResult<>(errorCode, errorMessage);
    }


}
