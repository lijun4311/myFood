package com.mhs66.service.impl;

import com.mhs66.mapper.OrdersMapper;
import com.mhs66.pojo.Orders;
import com.mhs66.service.IOrdersService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
public class OrdersServiceImpl extends BaseServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
