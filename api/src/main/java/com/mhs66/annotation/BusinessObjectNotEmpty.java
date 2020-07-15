package com.mhs66.annotation;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.annotation.*;

/**
 *description:
 * controller 参数是否为空效验
 * {@link com.mhs66.aspect.BusinessObjectAspect#checkBusinessObjectNotEmpty(ProceedingJoinPoint)}
 * 错误返回 {@link com.mhs66.WebResult#illegalParam()}
 *@author 76442
 *@date 2020-07-15 20:37
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BusinessObjectNotEmpty {

}
