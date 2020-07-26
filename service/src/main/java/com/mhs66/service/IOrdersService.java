package com.mhs66.service;

import com.mhs66.pojo.Orders;
import com.mhs66.pojo.bo.ShopcartBO;
import com.mhs66.pojo.bo.SubmitOrderBO;
import com.mhs66.pojo.vo.OrderVO;

import java.util.List;


/**
 * <p>
 * 订单表  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
public interface IOrdersService extends BaseService<Orders> {

    OrderVO createOrder(SubmitOrderBO submitOrderBO, List<ShopcartBO> shopcartList);

    void updateOrderStatus(String merchantOrderId, Integer type);
}
