package com.mhs66.pojo.vo;

import lombok.*;


/**
 *description:
 *用于展示商品搜索列表结果的VO
 *@author 76442
 *@date 2020-07-19 2:25
 */
@Data
public class SearchItemsVO {

    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;

}
