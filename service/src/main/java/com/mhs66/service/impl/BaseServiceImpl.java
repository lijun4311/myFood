package com.mhs66.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mhs66.annotation.ArgsNotEmpty;
import com.mhs66.exception.service.MyBatisPlusException;
import com.mhs66.pojo.bo.IPageBo;
import com.mhs66.service.BaseService;
import com.mhs66.utils.IWrapperUtil;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author lijun
 * @Date 2020-05-25 19:24
 * @Description service实现类 基类
 * @Since version-1.0
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
    protected final static Logger log = LoggerFactory.getLogger("AsyncLogger");

    @Override
    @ArgsNotEmpty("queryWrapper")
    public Page<T> getPage(IPageBo iPageBo, QueryWrapper<T> queryWrapper, Class<T> tClass) {
        Page<T> pageData = new Page<>(iPageBo.getCurrent(), iPageBo.getSize());
        queryWrapper = IWrapperUtil.getQueryWrapperPage(iPageBo, tClass, queryWrapper);
        pageData = this.page(pageData, queryWrapper);
        return pageData;
    }

    @Override
    @ArgsNotEmpty
    public Page<T> getPage(IPageBo iPageBo) {
        Page<T> pageData = new Page<>(iPageBo.getCurrent(), iPageBo.getSize());
        pageData = this.page(pageData);
        return pageData;
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper) {

        try {
            return super.getOne(queryWrapper);
        } catch (MyBatisSystemException e) {
            throw new MyBatisPlusException("数据结果重复,请联系管理员", e);
        }

    }


    @Override
    public LambdaQueryWrapper<T> iLambdaQuery() {
        return Wrappers.lambdaQuery();
    }

}
