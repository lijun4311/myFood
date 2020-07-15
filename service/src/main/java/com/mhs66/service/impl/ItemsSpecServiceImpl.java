package com.mhs66.service.impl;

import com.mhs66.mapper.ItemsSpecMapper;
import com.mhs66.pojo.ItemsSpec;
import com.mhs66.service.IItemsSpecService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
public class ItemsSpecServiceImpl extends BaseServiceImpl<ItemsSpecMapper, ItemsSpec> implements IItemsSpecService {

}
