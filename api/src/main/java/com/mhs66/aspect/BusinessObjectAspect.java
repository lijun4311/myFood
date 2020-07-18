package com.mhs66.aspect;

import com.google.common.collect.Maps;
import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.utils.IBeanUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * description:
 * 验证业务传输对象是否为空
 *
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
            for (Object arg : args) {
                if (webParamNotEmpty.strict()) {
                    if (IBeanUtil.isValueBankExclude(args)) {
                        return WebResult.illegalParam();
                    }
                } else {
                    if (IBeanUtil.isRequired(arg)) {
                        return WebResult.illegalParam();
                    }
                }

            }
        } else {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (Annotation[] parameterAnnotation : parameterAnnotations) {
                int paramIndex = ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
                Object paramValue = args[paramIndex];
                //判断是否有参数效验结果集
                if (paramValue instanceof BindingResult) {
                    BindingResult result = ((BindingResult) paramValue);
                    // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
                    if (result.hasErrors()) {
                        Map<String, String> map = Maps.newHashMap();
                        List<FieldError> errorList = result.getFieldErrors();
                        for (FieldError error : errorList) {
                            // 发生验证错误所对应的某一个属性
                            String errorField = error.getField();
                            // 验证错误的信息
                            String errorMsg = error.getDefaultMessage();
                            map.put(errorField, errorMsg);
                            return WebResult.illegalParamMap(map);
                        }
                    }

                }
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation instanceof BusinessObjectNotEmpty) {
                        if (((BusinessObjectNotEmpty) annotation).strict()) {
                            if (IBeanUtil.isValueBankExclude(paramValue)) {
                                return WebResult.illegalParam();
                            }
                        } else {
                            if (IBeanUtil.isRequired(paramValue)) {
                                return WebResult.illegalParam();
                            }
                        }
                    }
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
