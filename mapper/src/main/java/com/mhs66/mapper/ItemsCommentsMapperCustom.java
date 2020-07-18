package com.mhs66.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mhs66.pojo.ItemsComments;
import com.mhs66.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 *description:
 *
 *@author 76442
 *@date 2020-07-19 2:22
 */
public interface ItemsCommentsMapperCustom extends BaseMapper<ItemsComments> {

    public void saveComments(Map<String, Object> map);

    public List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}