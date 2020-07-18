package com.mhs66.exception;

import com.mhs66.WebResult;
import com.mhs66.config.ILogBase;
import com.mhs66.exception.service.ServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author silence
 */
@RestControllerAdvice
public class GlobalExceptionHandler implements ILogBase {


    /**
     * 服务异常
     */
    @ExceptionHandler(ServiceException.class)
    public WebResult serviceException(Exception e) {
        log.error(e.getMessage(), e);
        return WebResult.errorMsg(e.getMessage() + "请联系管理员");
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public WebResult illegalException(Exception e) {
        log.error(e.getMessage(), e);
        return WebResult.errorMsg("参数错误");
    }
}
