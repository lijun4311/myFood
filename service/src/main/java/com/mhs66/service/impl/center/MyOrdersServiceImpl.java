package com.mhs66.service.impl.center;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;

import com.mhs66.enums.BusinessStatus;
import com.mhs66.enums.PaymentStatus;
import com.mhs66.mapper.OrderStatusMapper;
import com.mhs66.mapper.OrdersMapper;
import com.mhs66.mapper.OrdersMapperCustom;
import com.mhs66.pojo.OrderStatus;
import com.mhs66.pojo.Orders;
import com.mhs66.pojo.vo.MyOrdersVO;
import com.mhs66.pojo.vo.OrderStatusCountsVO;
import com.mhs66.pojo.vo.PageVo;
import com.mhs66.service.center.MyOrdersService;
import com.mhs66.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MyOrdersServiceImpl extends BaseServiceImpl<OrdersMapper,Orders> implements MyOrdersService {


    public final OrdersMapperCustom ordersMapperCustom;


    public final OrdersMapper ordersMapper;


    public final OrderStatusMapper orderStatusMapper;


    @Override
    public PageVo queryMyOrders(String userId,
                                Integer orderStatus,
                                Integer page,
                                Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }

        PageHelper.startPage(page, pageSize);

        List<MyOrdersVO> list = ordersMapperCustom.queryMyOrders(map);

        return setPagedVo(list, page);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {
        LambdaUpdateWrapper<OrderStatus> luw = new LambdaUpdateWrapper<OrderStatus>().eq(OrderStatus::getOrderId, orderId).eq(OrderStatus::getOrderStatus, PaymentStatus.WAIT_DELIVER.type);
        luw.set(OrderStatus::getOrderStatus, PaymentStatus.WAIT_RECEIVE.type);
        orderStatusMapper.update(null, luw);
    }


    @Override
    public Orders queryMyOrder(String userId, String orderId) {

        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setId(orderId);
        orders.setIsDelete(BusinessStatus.NO.type);
        return ordersMapper.selectOne(Wrappers.lambdaQuery(orders));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {


        LambdaUpdateWrapper<OrderStatus> luw = new LambdaUpdateWrapper<OrderStatus>()
                .eq(OrderStatus::getOrderId, orderId).eq(OrderStatus::getOrderStatus, PaymentStatus.WAIT_RECEIVE.type);
        luw.set(OrderStatus::getOrderStatus, PaymentStatus.SUCCESS.type);
        orderStatusMapper.update(null, luw);
        int result = orderStatusMapper.update(null, luw);

        return result == 1;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {


        LambdaUpdateWrapper<Orders> luw = new LambdaUpdateWrapper<Orders>()
                .eq(Orders::getId, orderId).eq(Orders::getUserId, userId);
        luw.set(Orders::getIsDelete, BusinessStatus.YES.type);

        int result = ordersMapper.update(null, luw);

        return result == 1;
    }


    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        map.put("orderStatus", PaymentStatus.WAIT_PAY.type);
        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", PaymentStatus.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", PaymentStatus.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", PaymentStatus.SUCCESS.type);
        map.put("isComment", BusinessStatus.NO.type);
        int waitCommentCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        OrderStatusCountsVO countsVO = new OrderStatusCountsVO(waitPayCounts,
                waitDeliverCounts,
                waitReceiveCounts,
                waitCommentCounts);
        return countsVO;
    }


    @Override
    public PageVo getOrdersTrend(String userId, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<OrderStatus> list = ordersMapperCustom.getMyOrderTrend(map);

        return setPagedVo(list, page);
    }


}
