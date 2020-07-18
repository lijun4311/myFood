package com.mhs66.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 *description:
 * ybatis_plus 数据自动填充实现类
 *@author 76442
 *@date 2020-07-18 23:56
 */
@Component
public class MybatisFillHandler implements MetaObjectHandler {
    protected final static Logger log = LoggerFactory.getLogger("AsyncLogger");

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
