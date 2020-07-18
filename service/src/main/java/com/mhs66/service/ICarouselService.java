package com.mhs66.service;

import com.mhs66.pojo.Carousel;

import java.util.List;

/**
 * <p>
 * 轮播图  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
public interface ICarouselService extends BaseService<Carousel> {
    /**
     * 获取首页轮播图列表
     * @param type 是否显示
     * @return 列表
     */
    List<Carousel> queryAll(Integer type);
}
