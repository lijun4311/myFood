package com.mhs66.pojo.vo;

import lombok.*;

/**
 *description:
 *用户中心，我的订单列表嵌套商品VO
 *@author 76442
 *@date 2020-07-19 2:24
 */
@Data
public class MySubOrderItemVO {

    private String itemId;
    private String itemImg;
    private String itemName;
    private String itemSpecName;
    private Integer buyCounts;
    private Integer price;

}