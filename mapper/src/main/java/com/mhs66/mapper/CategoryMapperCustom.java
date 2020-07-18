package com.mhs66.mapper;


import com.mhs66.pojo.vo.CategoryVO;
import com.mhs66.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 *description:
 *
 *@author 76442
 *@date 2020-07-19 2:22
 */
public interface CategoryMapperCustom {

    public List<CategoryVO> getSubCatList(Integer rootCatId);

    public List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}