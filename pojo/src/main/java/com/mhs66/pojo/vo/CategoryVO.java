package com.mhs66.pojo.vo;

import lombok.Data;

import java.util.List;


/**
 *description:
 *二级分类VO
 *@author 76442
 *@date 2020-07-19 2:23
 */
@Data
public class CategoryVO {

    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    /**
     * 三级分类vo list
     */

    private List<SubCategoryVO> subCatList;


}
