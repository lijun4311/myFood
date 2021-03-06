package com.mhs66.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mhs66.enums.BusinessStatus;

import com.mhs66.enums.PaymentStatus;
import com.mhs66.mapper.OrdersMapper;
import com.mhs66.pojo.*;
import com.mhs66.pojo.bo.ShopcartBO;
import com.mhs66.pojo.bo.SubmitOrderBO;
import com.mhs66.pojo.vo.MerchantOrdersVO;
import com.mhs66.pojo.vo.OrderVO;
import com.mhs66.service.*;
import com.mhs66.utils.IStringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 订单表  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-15
 */
@Service
@AllArgsConstructor
public class OrdersServiceImpl extends BaseServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    private final IUserAddressService addressService;
    private final IItemsService itemsService;
    private final IItemsSpecService iItemsSpecService;
    private final IItemsImgService iItemsImgService;
    private final IOrderItemsService orderItemsService;
    private final IOrderStatusService orderStatusService;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO,List<ShopcartBO> shopcartList) {

        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为0
        Integer postAmount = 0;

        String orderId = UUID.randomUUID().toString();

        UserAddress address = addressService.queryUserAddres(userId, addressId);

        // 1. 新订单数据保存
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);

        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + IStringUtil.SPACE
                + address.getCity() + IStringUtil.SPACE
                + address.getDistrict() + IStringUtil.SPACE
                + address.getDetail());

//        newOrder.setTotalAmount();
//        newOrder.setRealPayAmount();
        newOrder.setPostAmount(postAmount);

        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);

        newOrder.setIsComment(BusinessStatus.NO.type);
        newOrder.setIsDelete(BusinessStatus.NO.type);
        newOrder.setCreateTime(LocalDateTime.now());
        newOrder.setUpdateTime(LocalDateTime.now());


        // 2. 循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        // 商品原价累计
        int totalAmount = 0;
        // 优惠后的实际支付价格累计
        int realPayAmount = 0;

        List<ShopcartBO> toBeRemovedShopcatdList = new ArrayList<>();
        for (String itemSpecId : itemSpecIdArr) {
            ShopcartBO cartItem = getBuyCountsFromShopcart(shopcartList, itemSpecId);

            // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
            int buyCounts = cartItem.getBuyCounts();
            toBeRemovedShopcatdList.add(cartItem);

            // 2.1 根据规格id，查询规格的具体信息，主要获取价格
            ItemsSpec itemSpec = iItemsSpecService.getById(itemSpecId);
            totalAmount += itemSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemSpec.getPriceDiscount() * buyCounts;

            // 2.2 根据商品id，获得商品信息以及商品图片
            String itemId = itemSpec.getItemId();
            Items item = itemsService.getById(itemId);
            String imgUrl = iItemsImgService.queryItemMainImgById(itemId);

            // 2.3 循环保存子订单数据到数据库
            String subOrderId = UUID.randomUUID().toString();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemSpec.getName());
            subOrderItem.setPrice(itemSpec.getPriceDiscount());
            orderItemsService.save(subOrderItem);

            // 2.4 在用户提交订单以后，规格表中需要扣除库存
            itemsService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }

        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        save(newOrder);

        // 3. 保存订单状态表
        com.mhs66.pojo.OrderStatus waitPayOrderStatus = new com.mhs66.pojo.OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(PaymentStatus.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(LocalDateTime.now());
        orderStatusService.save(waitPayOrderStatus);

        // 4. 构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        // 5. 构建自定义订单vo
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        orderVO.setToBeRemovedShopcatdList(toBeRemovedShopcatdList);
        return orderVO;
    }

    @Override
    public void updateOrderStatus(String merchantOrderId, Integer type) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(merchantOrderId);
        paidStatus.setOrderStatus(type);
        paidStatus.setPayTime(LocalDateTime.now());
        orderStatusService.update(Wrappers.lambdaQuery(paidStatus));

    }

    /**
     *  获得选择商品
     *
     * @param shopcartList 购物车列表
     * @param specId 商品id
     * @return 商品
     */
    private ShopcartBO getBuyCountsFromShopcart(List<ShopcartBO> shopcartList, String specId) {
        for (ShopcartBO cart : shopcartList) {
            if (cart.getSpecId().equals(specId)) {
                return cart;
            }
        }
        return null;
    }

}
