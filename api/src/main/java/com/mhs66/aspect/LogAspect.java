package com.mhs66.aspect;

import com.google.common.collect.Maps;
import com.mhs66.annotation.Log;
import com.mhs66.config.ILogBase;
import com.mhs66.consts.UserConsts;
import com.mhs66.enums.BusinessStatus;
import com.mhs66.manager.AsyncManager;
import com.mhs66.manager.factory.AsyncFactory;
import com.mhs66.pojo.OperLog;
import com.mhs66.pojo.Users;
import com.mhs66.utils.CookieUtil;
import com.mhs66.utils.JsonUtil;
import com.mhs66.utils.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * description:
 * 服务层运行时间日志输出
 *
 * @author 76442
 * @date 2020-07-15 20:55
 */
@Aspect
@Component
public class LogAspect implements ILogBase {
    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.mhs66.annotation.Log)")
    public void logPointCut() {
    }


    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            HttpServletRequest request = ServletUtil.getRequest();


            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            Users currentUser = JsonUtil.stringToObj(CookieUtil.getCookieValueDecoder(request, UserConsts.getUserPrefix()), Users.class);

            // *========数据库日志=========*//
            OperLog operLog = new OperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址

            operLog.setOperIp(request.getRemoteAddr());
            Map<String, Object> data = Maps.newHashMap();
            if (controllerLog.isSaveRequestData()) {
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                String[] parameterNames = signature.getParameterNames();
                Object[] args = joinPoint.getArgs();
                int i;
                for (i = 0; i < args.length; i++) {
                    if (!(args[i] instanceof BindingResult)) {
                        data.put(parameterNames[i], args[i]);
                    }
                }
                // 获取参数的信息，传入到数据库中。
            }
            operLog.setOperParam(JsonUtil.objToString(data));
            operLog.setOperUrl(request.getRequestURI());
            if (currentUser != null) {
                operLog.setOperName(currentUser.getUsername());
            }

            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            // 保存数据库

            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(Log log, OperLog operLog) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值

    }


    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

}
