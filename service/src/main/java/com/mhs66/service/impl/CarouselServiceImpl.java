package com.mhs66.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mhs66.annotation.ArgsNotNull;
import com.mhs66.mapper.CarouselMapper;
import com.mhs66.pojo.Carousel;
import com.mhs66.service.ICarouselService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 轮播图  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
public class CarouselServiceImpl extends BaseServiceImpl<CarouselMapper, Carousel> implements ICarouselService {

    @Override
    @ArgsNotNull
    public List<Carousel> queryAll(Integer type) {


        return this.list(new LambdaQueryWrapper<Carousel>().eq(Carousel::getIsShow, type).orderByAsc(Carousel::getSort));


    }
}
