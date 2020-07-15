package com.mhs66.service.impl;

import com.mhs66.mapper.OrderItemsMapper;
import com.mhs66.pojo.OrderItems;
import com.mhs66.service.IOrderItemsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单商品关联表  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
public class OrderItemsServiceImpl extends BaseServiceImpl<OrderItemsMapper, OrderItems> implements IOrderItemsService {

}
