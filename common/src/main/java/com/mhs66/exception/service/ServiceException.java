package com.mhs66.exception.service;

/**
 * description:
 * 服务异常
 *
 * @author 76442
 * @date 2020-07-18 20:23
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected final String message;

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
