package com.mhs66.service.impl;

import com.github.pagehelper.PageHelper;
import com.mhs66.exception.service.ServiceException;
import com.mhs66.mapper.ItemsCommentsMapperCustom;
import com.mhs66.mapper.ItemsMapper;
import com.mhs66.mapper.ItemsMapperCustom;
import com.mhs66.pojo.Items;
import com.mhs66.pojo.bo.PageBo;
import com.mhs66.pojo.vo.*;
import com.mhs66.service.IItemsService;
import com.mhs66.utils.DesensitizationUtil;
import com.mhs66.utils.lambda.ILambdaUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
@AllArgsConstructor
public class ItemsServiceImpl extends BaseServiceImpl<ItemsMapper, Items> implements IItemsService {
    private final ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    private final ItemsMapperCustom itemsMapperCustom;

    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        return itemsCommentsMapperCustom.queryCommentCounts(itemId);
    }

    @Override
    public PageVo<ItemCommentVO> queryPagedComments(String itemId, Integer level, int page, int pageSize) {
        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);
        for (ItemCommentVO vo : list) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        return setPagedVo(list, page);
    }

    @Override
    public PageVo searhItems(PageBo pageBo) {
        int page = pageBo.getPage();

        startPage(pageBo);
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(toMap(pageBo));

        return setPagedVo(list, page);
    }


    @Override
    public PageVo searhItems(Integer catId, PageBo pageBo) {
        int page = pageBo.getPage();
        Map<String, Object> map = toMap(pageBo);
        map.put(ILambdaUtil.getFieldName(Items::getCatId), catId);
        startPage(pageBo);
        List<SearchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(map);
        return setPagedVo(list, page);
    }

    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {

        String[] ids = specIds.split(",");
        List<String> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList, ids);
        return itemsMapperCustom.queryItemsBySpecIds(specIdsList);
    }

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    @Override
    public void decreaseItemSpecStock(String specId, int buyCounts) {
        int result = itemsMapperCustom.decreaseItemSpecStock(specId, buyCounts);
        if (result != 1) {
            throw new ServiceException("订单创建失败，原因：库存不足!");
        }
    }
}
