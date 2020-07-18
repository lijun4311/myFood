package com.mhs66.pojo.vo;

import lombok.*;

import java.util.List;


/**
 *description:
 *最新商品VO
 *@author 76442
 *@date 2020-07-19 2:24
 */
@Data
public class NewItemsVO {

    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVO> simpleItemList;

}
