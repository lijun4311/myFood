package com.mhs66.aspect;

import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.utis.IBeanUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * description:
 * 验证业务传输对象是否为空
 * @author 76442
 * @date 2020-07-15 20:46
 */
@Aspect
@Component
public class BusinessObjectAspect {
    /**
     * @param proceedingJoinPoint 进行连接点
     * @return 方法结束返回对象
     * @throws Throwable 异常父类
     */
    @Around("execution(public * com.mhs66.controller..*.*(..))")
    public Object checkBusinessObjectNotEmpty(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        Method method = signature.getMethod();
        BusinessObjectNotEmpty webParamNotEmpty = method.getAnnotation(BusinessObjectNotEmpty.class);
        if (webParamNotEmpty != null) {
            if (IBeanUtil.isRequired(args)) {
                return WebResult.illegalParam();
            }
        } else {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (Annotation[] parameterAnnotation : parameterAnnotations) {
                int paramIndex = ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation instanceof BusinessObjectNotEmpty) {
                        Object paramValue = args[paramIndex];
                        if (IBeanUtil.isRequired(paramValue)) {
                            return WebResult.illegalParam();
                        }
                    }
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }

}
