package com.mhs66.pojo.vo;

import lombok.Data;

/**
 *description:
 * 用于展示商品评价数量的vo
 *@author 76442
 *@date 2020-07-19 2:23
 */
@Data
public class CommentLevelCountsVO {

    public Integer totalCounts;
    public Integer goodCounts;
    public Integer normalCounts;
    public Integer badCounts;


}
