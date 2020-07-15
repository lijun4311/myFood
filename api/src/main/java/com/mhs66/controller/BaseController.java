package com.mhs66.controller;


import com.mhs66.config.ILogBase;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * description:
 * 控制器基类
 *
 * @author 76442
 * @date 2020-07-15 21:48
 */
public class BaseController implements ILogBase {
@Autowired
    protected HttpServletRequest request;
@Autowired
protected HttpServletResponse response;
@Autowired
protected HttpSession session;

    /**
     * 获得登录用户对象
     *
     * @return 用户信息
     */
/*    protected Users getUser() {
        return (User) request.getAttribute(UserEnum.REQUEST_USER.getName());
    }*/

    /**
     * 获得用户登录token
     *
     * @return token
     */
/*  protected String getLoginToken() {
        return (String) request.getAttribute(UserEnum.REQUEST_TOKEN.getName());
    }*/
}
