package com.mhs66.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mhs66.pojo.bo.PageBo;
import com.mhs66.pojo.vo.PageVo;

import java.util.List;
import java.util.Map;

/**
 * @Author lijun
 * @Date 2020-05-25 19:22
 * @Description service 基类
 * @Since version-1.0
 */
public interface BaseService<T> extends IService<T> {
    /**
     * 链式查询 lambda 式
     *
     *
     * @return LambdaQueryWrapper
     */
    LambdaQueryWrapper iLambdaQuery();


    <R> PageVo<R> setPagedVo(List<R> list, Integer page);

    void startPage(PageBo bo);

    Map<String, Object> toMap(PageBo pageBo);
}
