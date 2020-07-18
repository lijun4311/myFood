package com.mhs66.pojo.vo;

import lombok.*;

import java.util.Date;
import java.util.List;


/**
 *description:
 * 用户中心，我的订单列表VO
 *@author 76442
 *@date 2020-07-19 2:24
 */
@Data
public class MyOrdersVO {

    private String orderId;
    private Date createTime;
    private Integer payMethod;
    private Integer realPayAmount;
    private Integer postAmount;
    private Integer isComment;
    private Integer orderStatus;

    private List<MySubOrderItemVO> subOrderItemList;

}