package com.mhs66.pojo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 商品评价表 
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ItemsComments extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id 用户名须脱敏
     */
    private String userId;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 商品规格id 可为空
     */
    private String itemSpecId;

    /**
     * 规格名称 可为空
     */
    private String sepcName;

    /**
     * 评价等级 1：好评 2：中评 3：差评
     */
    private Integer commentLevel;

    /**
     * 评价内容
     */
    private String content;




}
