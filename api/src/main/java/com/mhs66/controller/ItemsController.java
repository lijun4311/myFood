package com.mhs66.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.pojo.Items;
import com.mhs66.pojo.ItemsImg;
import com.mhs66.pojo.ItemsParam;
import com.mhs66.pojo.ItemsSpec;
import com.mhs66.pojo.bo.PageBo;
import com.mhs66.pojo.vo.*;
import com.mhs66.service.IItemsImgService;
import com.mhs66.service.IItemsParamService;
import com.mhs66.service.IItemsService;
import com.mhs66.service.IItemsSpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
@AllArgsConstructor
public class ItemsController extends BaseController {


    private final IItemsService itemService;
    private final IItemsImgService iItemsImgService;
    private final IItemsSpecService iItemsSpecService;
    private final IItemsParamService iItemsParamService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    @BusinessObjectNotEmpty
    public WebResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {
        Items item = itemService.getById(itemId);
        List<ItemsImg> itemImgList = iItemsImgService.list(Wrappers.lambdaQuery(ItemsImg.class).eq(ItemsImg::getItemId, itemId));
        List<ItemsSpec> itemsSpecList = iItemsSpecService.list(Wrappers.lambdaQuery(ItemsSpec.class).eq(ItemsSpec::getItemId, itemId));
        ItemsParam itemsParam = iItemsParamService.getOne(Wrappers.lambdaQuery(ItemsParam.class).eq(ItemsParam::getItemId, itemId));
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);
        return WebResult.okData(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public WebResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {

        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);
        return WebResult.okData(countsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public WebResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam
            @BusinessObjectNotEmpty String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam Integer level,
            PageBo pageBo) {

        PageVo<ItemCommentVO> vo = itemService.queryPagedComments(itemId, level, pageBo.getPage(), pageBo.getPageSize());
        return WebResult.okData(vo);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public WebResult search(@Validated({PageBo.serarch.class}) PageBo pageBo) {
        PageVo vo = itemService.searhItems(pageBo);
        return WebResult.okData(vo);
    }

    @ApiOperation(value = "通过分类id搜索商品列表", notes = "通过分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public WebResult catItems(
            @BusinessObjectNotEmpty @ApiParam(name = "catId", value = "三级分类id", required = true)
            @RequestParam Integer catId, PageBo pageBo) {
        PageVo vo = itemService.searhItems(catId, pageBo);
        return WebResult.okData(vo);
    }

    // 用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格），类似京东淘宝
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public WebResult refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true, example = "1001,1003,1005")
            @RequestParam
            @BusinessObjectNotEmpty String itemSpecIds) {
        List<ShopcartVO> list = itemService.queryItemsBySpecIds(itemSpecIds);

        return WebResult.okData(list);
    }
}
