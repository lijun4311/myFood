package com.mhs66.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mhs66.annotation.ArgsNotEmpty;
import com.mhs66.exception.service.MyBatisPlusException;
import com.mhs66.pojo.bo.PageBo;
import com.mhs66.pojo.vo.PageVo;
import com.mhs66.service.BaseService;
import com.mhs66.utils.IStringUtil;
import com.mhs66.utils.lambda.ILambdaUtil;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lijun
 * @Date 2020-05-25 19:24
 * @Description service实现类 基类
 * @Since version-1.0
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
    protected final static Logger log = LoggerFactory.getLogger("AsyncLogger");

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

    @Override
    public <R> PageVo<R> setPagedVo(List<R> list, Integer page) {
        PageInfo<R> pageList = new PageInfo<>(list);
        PageVo<R> vo = new PageVo<>();
        vo.setPage(page);
        vo.setRows(list);
        vo.setTotal(pageList.getPages());
        vo.setRecords(pageList.getTotal());
        return vo;
    }
    @Override
    public void startPage(PageBo bo){
        PageHelper.startPage(bo.getPage(), bo.getPageSize());
    }
    @Override
    public Map<String, Object> toMap(PageBo pageBo) {
        Map<String, Object> map = new HashMap<>();
        if (IStringUtil.isNotEmpty(pageBo.getKeywords())) {
            map.put(ILambdaUtil.getFieldName(PageBo::getKeywords), pageBo.getKeywords());
        }
        if (IStringUtil.isNotEmpty(pageBo.getSort())) {
            map.put(ILambdaUtil.getFieldName(PageBo::getSort), pageBo.getSort());
        }

        return map;
    }
}
