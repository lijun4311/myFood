package com.mhs66.pojo.vo;

import lombok.*;


/**
 *description:
 *订单状态概览数量VO
 *@author 76442
 *@date 2020-07-19 2:25
 */
@Data
@AllArgsConstructor
public class OrderStatusCountsVO {

    private Integer waitPayCounts;
    private Integer waitDeliverCounts;
    private Integer waitReceiveCounts;
    private Integer waitCommentCounts;



}