package com.mhs66.controller;


import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.consts.RedisPrefix;
import com.mhs66.enums.BusinessStatus;
import com.mhs66.pojo.Carousel;
import com.mhs66.pojo.Category;
import com.mhs66.pojo.vo.CategoryVO;
import com.mhs66.pojo.vo.NewItemsVO;
import com.mhs66.service.ICarouselService;
import com.mhs66.service.ICategoryService;
import com.mhs66.utils.JsonUtil;
import com.mhs66.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
@AllArgsConstructor
public class IndexController extends BaseController {


    private final RedisOperator redisOperator;

    private final ICarouselService carouselService;


    private final ICategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public WebResult carousel() {


        List<Carousel> list;
        String carouselStr = redisOperator.get(RedisPrefix.CAROUSEL);
        if (StringUtils.isBlank(carouselStr)) {
            list = carouselService.queryAll(BusinessStatus.YES.type);
            redisOperator.set(RedisPrefix.CAROUSEL, JsonUtil.objToString(list));
        } else {
            list = JsonUtil.stringToObj(carouselStr, List.class, Carousel.class);
        }

        return WebResult.okData(list);
    }

    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping("/cats")
    public WebResult cats() {

        List<Category> list;
        String catsStr = redisOperator.get(RedisPrefix.CATS);
        if (StringUtils.isBlank(catsStr)) {
            list = categoryService.queryAllRootLevelCat();
            redisOperator.set(RedisPrefix.CATS, JsonUtil.objToString(list));
        } else {
            list = JsonUtil.stringToObj(catsStr, List.class, Carousel.class);

        }


        return WebResult.okData(list);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    @BusinessObjectNotEmpty
    public WebResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        List<CategoryVO> list;
        String catsStr = redisOperator.get(RedisPrefix.SUBCAT);
        if (StringUtils.isBlank(catsStr)) {
            list = categoryService.getSubCatList(rootCatId);
            redisOperator.set(RedisPrefix.SUBCAT, JsonUtil.objToString(list));
        } else {
            list = JsonUtil.stringToObj(catsStr, List.class, Carousel.class);
        }
        return WebResult.okData(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    @BusinessObjectNotEmpty
    public WebResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return WebResult.okData(list);
    }

}
