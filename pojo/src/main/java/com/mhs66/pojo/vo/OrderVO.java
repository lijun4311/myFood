package com.mhs66.pojo.vo;

import com.mhs66.pojo.bo.ShopcartBO;
import lombok.Data;

import java.util.List;

/**
 *description:
 *
 *@author 76442
 *@date 2020-07-19 2:25
 */
@Data
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
    private List<ShopcartBO> toBeRemovedShopcatdList;


}