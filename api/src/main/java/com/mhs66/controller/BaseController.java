package com.mhs66.controller;


import com.mhs66.config.ILogBase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * description:
 * 控制器基类
 *
 * @author 76442
 * @date 2020-07-15 21:48
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BaseController implements ILogBase {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    /**
     * 数据业务效验封装
     * @param result 效验结果
     * @return map结果
     */
    protected Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();

            map.put(errorField, errorMsg);
        }
        return map;
    }
}
