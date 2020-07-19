package com.mhs66.controller;


import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.annotation.Log;
import com.mhs66.consts.UserConsts;
import com.mhs66.enums.BusinessType;
import com.mhs66.pojo.Users;
import com.mhs66.pojo.bo.UserBO;
import com.mhs66.service.IUsersService;
import com.mhs66.utils.CookieUtil;
import com.mhs66.utils.JsonUtil;
import com.mhs66.utils.Md5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * description:
 * 用于注册登录的相关接口
 *
 * @author 76442
 * @date 2020-07-18 16:07
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PassportController extends BaseController {

    private final IUsersService userService;


    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    @BusinessObjectNotEmpty
    public WebResult usernameIsExist(@RequestParam String username) {
        //查找注册的用户名是否存在
        if (userService.queryUsernameIsExist(username)) {
            return WebResult.errorMsg("用户名已经存在");
        }
        return WebResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public WebResult regist(@RequestBody @Validated(UserBO.Regist.class) UserBO userBO, BindingResult result) {
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
        userService.setNullProperty(userResult);
        CookieUtil.setCookieEncode(request, response, UserConsts.getUserPrefix(),
                JsonUtil.objToString(userResult));
        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据
        return WebResult.ok();
    }

    @Log(title = "用户登录", businessType = BusinessType.GRANT)
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public WebResult login(@RequestBody @Validated(UserBO.Login.class) UserBO userBO, BindingResult result
    ) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 1. 实现登录
        Users userResult = userService.queryUserForLogin(username,
                Md5Util.md5EncodeUtf8(password));
        if (userResult == null) {
            return WebResult.errorMsg("用户名或密码不正确");
        }
        userService.setNullProperty(userResult);
        CookieUtil.setCookieEncode(request, response, UserConsts.getUserPrefix(),
                JsonUtil.objToString(userResult));

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据

        return WebResult.okData(userResult);
    }


    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "DELETE")
    @DeleteMapping("/logout")
    public WebResult logout() {

        // 清除用户的相关信息的cookie
        CookieUtil.deleteCookie(request, response, UserConsts.getUserPrefix());

        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据

        return WebResult.ok();
    }
}
