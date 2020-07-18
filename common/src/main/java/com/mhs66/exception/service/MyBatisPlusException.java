package com.mhs66.exception.service;

/**
 * description:
 *
 * @author 76442
 * @date 2020-07-18 20:20
 */
public class MyBatisPlusException  extends ServiceException{
    public MyBatisPlusException(String message) {
        super(message);
    }

    public MyBatisPlusException(String message, Throwable e) {
        super(message, e);
    }


}
