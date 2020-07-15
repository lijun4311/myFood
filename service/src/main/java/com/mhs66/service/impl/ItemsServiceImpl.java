package com.mhs66.service.impl;

import com.mhs66.mapper.ItemsMapper;
import com.mhs66.pojo.Items;
import com.mhs66.service.IItemsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
public class ItemsServiceImpl extends BaseServiceImpl<ItemsMapper, Items> implements IItemsService {

}
