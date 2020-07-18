package com.mhs66.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;


/**
 *description:
 * 实体类基类
 *@author 76442
 *@date 2020-07-18 16:11
 */
@Data
public abstract  class BaseEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;



}
