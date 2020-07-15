package com.mhs66.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 商品图片 
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ItemsImg extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品外键id 商品外键id
     */
    private String itemId;

    /**
     * 图片地址 图片地址
     */
    private String url;

    /**
     * 顺序 图片顺序，从小到大
     */
    private Integer sort;

    /**
     * 是否主图 是否主图，1：是，0：否
     */
    private Integer isMain;




}
