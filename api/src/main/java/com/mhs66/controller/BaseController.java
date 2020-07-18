package com.mhs66.controller;


import com.mhs66.config.ILogBase;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * description:
 * 控制器基类
 *
 * @author 76442
 * @date 2020-07-15 21:48
 */

public abstract class BaseController implements ILogBase {
    @Autowired
    protected  HttpServletRequest request;

    @Autowired
    protected  HttpServletResponse response;


}
