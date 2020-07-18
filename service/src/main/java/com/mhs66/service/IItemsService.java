package com.mhs66.service;

import com.mhs66.pojo.Items;
import com.mhs66.pojo.bo.PageBo;
import com.mhs66.pojo.vo.CommentLevelCountsVO;
import com.mhs66.pojo.vo.ItemCommentVO;
import com.mhs66.pojo.vo.PageVo;
import com.mhs66.pojo.vo.ShopcartVO;

import java.util.List;

/**
 * <p>
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
public interface IItemsService extends BaseService<Items> {
    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId id
     *
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);


    PageVo<ItemCommentVO> queryPagedComments(String itemId, Integer level, int page, int pageSize);

    PageVo searhItems(PageBo pageBo);

    PageVo searhItems(Integer catId, PageBo pageBo);

    List<ShopcartVO> queryItemsBySpecIds(String itemSpecIds);

    void decreaseItemSpecStock(String itemSpecId, int buyCounts);
}
