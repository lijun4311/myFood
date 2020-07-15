package com.mhs66.aspect;


import com.mhs66.annotation.ArgsNotEmpty;
import com.mhs66.annotation.ArgsNotNull;
import com.mhs66.utis.IBeanUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *description:
 * StringAop 统一参数切面处理类
 *@author 76442
 *@date 2020-07-15 20:20
 */
@Aspect
@Component
public class ParamAspect {
    /**
     * {@link ArgsNotNull} 参数不为null aop处理方法
     *
     * @param joinPoint 连接点
     * @throws IllegalArgumentException 指定的值为负值
     */
    @Before("@annotation(com.mhs66.annotation.ArgsNotNull)")
    public void checkedNotNullBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = signature.getParameterNames();
        ArgsNotNull argsNotNull = method.getAnnotation(ArgsNotNull.class);
        String fieldName = argsNotNull.value();
        int i = ArrayUtils.indexOf(parameterNames, fieldName);
        args = i > -1 ? ArrayUtils.remove(args, i) : args;
        for (Object o : args) {
            if (IBeanUtil.isNull(o)) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * {@link ArgsNotEmpty} 参数不为空 aop处理方法
     *
     * @param joinPoint 连接点
     * @throws IllegalArgumentException 指定的值为负值
     */
    @Before("@annotation(com.mhs66.annotation.ArgsNotEmpty)")
    public void checkedNotEmptyBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = signature.getParameterNames();
        ArgsNotEmpty argsNotEmpty = method.getAnnotation(ArgsNotEmpty.class);
        String[] fieldNames = argsNotEmpty.value().split(",");
        for (String name : fieldNames) {
            int i = ArrayUtils.indexOf(parameterNames, name);
            parameterNames = i > -1 ? ArrayUtils.remove(parameterNames, i) : parameterNames;
            args = i > -1 ? ArrayUtils.remove(args, i) : args;
        }
        if (IBeanUtil.isRequired(args)) {
            throw new IllegalArgumentException();
        }
    }


}
