package com.mhs66.service.impl.center;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;

import com.mhs66.enums.BusinessStatus;
import com.mhs66.mapper.ItemsCommentsMapperCustom;
import com.mhs66.mapper.OrderItemsMapper;
import com.mhs66.mapper.OrderStatusMapper;
import com.mhs66.mapper.OrdersMapper;
import com.mhs66.pojo.ItemsComments;
import com.mhs66.pojo.OrderItems;
import com.mhs66.pojo.OrderStatus;
import com.mhs66.pojo.Orders;
import com.mhs66.pojo.bo.center.OrderItemsCommentBO;
import com.mhs66.pojo.vo.MyCommentVO;
import com.mhs66.pojo.vo.PageVo;
import com.mhs66.service.BaseService;
import com.mhs66.service.center.MyCommentsService;
import com.mhs66.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class MyCommentsServiceImpl extends BaseServiceImpl<ItemsCommentsMapperCustom,ItemsComments> implements MyCommentsService {


    public OrderItemsMapper orderItemsMapper;


    public OrdersMapper ordersMapper;


    public OrderStatusMapper orderStatusMapper;


    public ItemsCommentsMapperCustom itemsCommentsMapperCustom;


    @Override
    public List<OrderItems> queryPendingComment(String orderId) {

        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.selectList(Wrappers.lambdaQuery(query));

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId,
                             List<OrderItemsCommentBO> commentList) {

        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(UUID.randomUUID().toString());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(BusinessStatus.YES.type);
        ordersMapper.updateById(order);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(LocalDateTime.now());
        orderStatusMapper.updateById(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageVo queryMyComments(String userId,
                                  Integer page,
                                  Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        return setPagedVo(list, page);
    }
}
