package com.mhs66.pojo.vo;

import lombok.*;

import java.util.Date;
/**
 *description:
 *
 *@author 76442
 *@date 2020-07-19 2:25
 */
@Data
public class MyCommentVO {

    private String commentId;
    private String content;
    private Date createTime;
    private String itemId;
    private String itemName;
    private String specName;
    private String itemImg;

}