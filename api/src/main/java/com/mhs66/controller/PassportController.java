package com.mhs66.controller;


import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.pojo.Users;
import com.mhs66.pojo.bo.UserBO;
import com.mhs66.service.IUsersService;
import com.mhs66.utis.CookieUtil;
import com.mhs66.utis.JsonUtil;
import com.mhs66.utis.Md5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PassportController extends BaseController {

    private final IUsersService userService;


    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    @BusinessObjectNotEmpty
    public WebResult<String> usernameIsExist(@RequestParam String username) {
        //查找注册的用户名是否存在
        if (userService.queryUsernameIsExist(username)) {
            return WebResult.errorMsg("用户名已经存在");
        }
        return WebResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public WebResult<?> regist(@RequestBody @Valid UserBO userBO, BindingResult result) {
        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return WebResult.illegalParamMap(errorMap);
        }
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        //  查询用户名是否存在
        if (userService.queryUsernameIsExist(username)) {
            return WebResult.errorMsg("用户名已经存在");
        }
        //  判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            return WebResult.errorMsg("两次密码输入不一致");
        }

        // 实现注册
        Users userResult = userService.createUser(userBO);

        userResult = setNullProperty(userResult);

        CookieUtil.setCookieEncode(request, response, "user",
                JsonUtil.objToString(userResult));

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据

        return WebResult.ok();
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public WebResult login(@RequestBody UserBO userBO,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 0. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return WebResult.errorMsg("用户名或密码不能为空");
        }

        // 1. 实现登录
        Users userResult = userService.queryUserForLogin(username,
                Md5Util.md5EncodeUtf8(password));

        if (userResult == null) {
            return WebResult.errorMsg("用户名或密码不正确");
        }

        userResult = setNullProperty(userResult);

        CookieUtil.setCookieEncode(request, response, "user",
                JsonUtil.objToString(userResult));


        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据

        return WebResult.okData(userResult);
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreateTime(null);
        userResult.setUpdateTime(null);
        userResult.setBirthday(null);
        return userResult;
    }


    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public WebResult logout(@RequestParam String userId,
                            HttpServletRequest request,
                            HttpServletResponse response) {

        // 清除用户的相关信息的cookie
        CookieUtil.deleteCookie(request, response, "user");

        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据

        return WebResult.ok();
    }
}
