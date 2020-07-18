package com.mhs66.annotation;



import java.lang.annotation.*;

/**
 * @author lijun
 * @date 2020-06-16 18:58
 * @description 用户登录 用户对象{@link User}存入 requeest 中
 * 对象获取标示 {@link UserEnum#REQUEST_USER}
 * @error 错误返回 {@link Rest#noLgoin()}
 * @since version-1.0
 */

/**
 *description:
 * 用户登录 用户对象{@link com.mhs66.pojo.Users}存入 requeest 中
 *  对象获取标示 {@link UserEnum#REQUEST_USER}
 *  错误返回 {@link com.mhs66.WebResult#noLgoin()}
 *@author 76442
 *@date 2020-07-15 20:38
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UserLogin {
}
