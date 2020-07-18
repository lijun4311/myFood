package com.mhs66.pojo.vo;

import lombok.*;

import java.util.Date;


/**
 *description:
 *用于展示商品评价的VO
 *@author 76442
 *@date 2020-07-19 2:23
 */
@Data
public class ItemCommentVO {

    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String userFace;
    private String nickname;

}
