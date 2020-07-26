package com.mhs66.controller;


import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.annotation.Log;
import com.mhs66.consts.RedisPrefix;
import com.mhs66.consts.UserConsts;
import com.mhs66.enums.BusinessType;
import com.mhs66.pojo.Users;
import com.mhs66.pojo.bo.ShopcartBO;
import com.mhs66.pojo.bo.UserBO;
import com.mhs66.service.IUsersService;
import com.mhs66.utils.CookieUtil;
import com.mhs66.utils.JsonUtil;
import com.mhs66.utils.Md5Util;
import com.mhs66.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


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

    private final RedisOperator redisOperator;

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
        // 同步购物车数据
        synchShopcartData(userResult.getId(), request, response);

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
        synchShopcartData(userResult.getId(), request, response);

        return WebResult.okData(userResult);
    }


    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "DELETE")
    @DeleteMapping("/logout")
    public WebResult logout() {

        // 清除用户的相关信息的cookie
        CookieUtil.deleteCookie(request, response, UserConsts.getUserPrefix());


        // TODO 用户退出登录，需要清空购物车
        // 分布式会话中需要清除用户数据
        CookieUtil.deleteCookie(request, response, RedisPrefix.SHOPCART);

        // TODO 分布式会话中需要清除用户数据

        return WebResult.ok();
    }


    /**
     * 注册登录成功后，同步cookie和redis中的购物车数据
     */
    private void synchShopcartData(String userId, HttpServletRequest request,
                                   HttpServletResponse response) {

        /**
         * 1. redis中无数据，如果cookie中的购物车为空，那么这个时候不做任何处理
         *                 如果cookie中的购物车不为空，此时直接放入redis中
         * 2. redis中有数据，如果cookie中的购物车为空，那么直接把redis的购物车覆盖本地cookie
         *                 如果cookie中的购物车不为空，
         *                      如果cookie中的某个商品在redis中存在，
         *                      则以cookie为主，删除redis中的，
         *                      把cookie中的商品直接覆盖redis中（参考京东）
         * 3. 同步到redis中去了以后，覆盖本地cookie购物车的数据，保证本地购物车的数据是同步最新的
         */

        String redisKey = RedisPrefix.SHOPCART + ":" + userId;
        String cookieKey = RedisPrefix.SHOPCART;
        // 从redis中获取购物车
        String shopcartJsonRedis = redisOperator.get(redisKey);

        // 从cookie中获取购物车
        String shopcartStrCookie = CookieUtil.getCookieValueDecoder(request, cookieKey);

        if (StringUtils.isBlank(shopcartJsonRedis)) {
            // redis为空，cookie不为空，直接把cookie中的数据放入redis
            if (StringUtils.isNotBlank(shopcartStrCookie)) {
                redisOperator.set(redisKey, shopcartStrCookie);
            }
        } else {
            // redis不为空，cookie不为空，合并cookie和redis中购物车的商品数据（同一商品则覆盖redis）
            if (StringUtils.isNotBlank(shopcartStrCookie)) {

                /**
                 * 1. 已经存在的，把cookie中对应的数量，覆盖redis（参考京东）
                 * 2. 该项商品标记为待删除，统一放入一个待删除的list
                 * 3. 从cookie中清理所有的待删除list
                 * 4. 合并redis和cookie中的数据
                 * 5. 更新到redis和cookie中
                 */

                List<ShopcartBO> shopcartListRedis = JsonUtil.stringToObj(shopcartJsonRedis, List.class, ShopcartBO.class);
                List<ShopcartBO> shopcartListCookie = JsonUtil.stringToObj(shopcartStrCookie, List.class, ShopcartBO.class);

                // 定义一个待删除list
                List<ShopcartBO> pendingDeleteList = new ArrayList<>();

                for (ShopcartBO redisShopcart : shopcartListRedis) {
                    String redisSpecId = redisShopcart.getSpecId();

                    for (ShopcartBO cookieShopcart : shopcartListCookie) {
                        String cookieSpecId = cookieShopcart.getSpecId();

                        if (redisSpecId.equals(cookieSpecId)) {
                            // 覆盖购买数量，不累加，参考京东
                            redisShopcart.setBuyCounts(cookieShopcart.getBuyCounts());
                            // 把cookieShopcart放入待删除列表，用于最后的删除与合并
                            pendingDeleteList.add(cookieShopcart);
                        }

                    }
                }

                // 从现有cookie中删除对应的覆盖过的商品数据
                shopcartListCookie.removeAll(pendingDeleteList);

                // 合并两个list
                shopcartListRedis.addAll(shopcartListCookie);
                // 更新到redis和cookie
                CookieUtil.setCookieEncode(request, response, cookieKey, JsonUtil.objToString(shopcartListRedis));
                redisOperator.set(redisKey, JsonUtil.objToString(shopcartListRedis));
            } else {
                // redis不为空，cookie为空，直接把redis覆盖cookie
                CookieUtil.setCookieEncode(request, response, cookieKey, shopcartJsonRedis);
            }

        }
    }
}
