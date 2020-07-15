package com.mhs66.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mhs66.annotation.ArgsNotEmpty;
import com.mhs66.pojo.bo.IPageBo;

/**
 * @Author lijun
 * @Date 2020-05-25 19:22
 * @Description service 基类
 * @Since version-1.0
 */
public interface BaseService<T> extends IService<T> {
    /**
     * 通用分页查询默认
     * @param iPageBo 前端分页对象
     * @param queryWrapper 查询构造器 可以为 null
     * @param tClass 分页对象class
     * @return 分页数据
     */
    @ArgsNotEmpty("queryWrapper")
    Page<T> getPage(IPageBo iPageBo, QueryWrapper<T> queryWrapper, Class<T> tClass);

    /**
     * 通用分页查询
     * @param iPageBo
     * @return
     */
    Page<T> getPage(IPageBo iPageBo);
}
