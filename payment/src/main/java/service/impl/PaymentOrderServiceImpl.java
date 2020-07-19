package service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mhs66.enums.BusinessStatus;
import com.mhs66.enums.PaymentStatus;
import com.mhs66.service.impl.BaseServiceImpl;
import mapper.OrdersMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pojo.Orders;
import pojo.bo.MerchantOrdersBO;
import service.PaymentOrderService;

import java.util.Date;

@Service
public class PaymentOrderServiceImpl extends BaseServiceImpl<OrdersMapper, Orders> implements PaymentOrderService {




    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean createPaymentOrder(MerchantOrdersBO merchantOrdersBO) {


        Orders paymentOrder = new Orders();
        BeanUtils.copyProperties(merchantOrdersBO, paymentOrder);


        paymentOrder.setPayStatus(PaymentStatus.WAIT_PAY.type);
        paymentOrder.setComeFrom("天天吃货");
        paymentOrder.setIsDelete(BusinessStatus.NO.type);
        paymentOrder.setCreatedTime(new Date());

        return save(paymentOrder);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryOrderByStatus(String merchantUserId, String merchantOrderId, Integer orderStatus) {

        Orders queryOrder = new Orders();
        queryOrder.setMerchantOrderId(merchantOrderId);
        queryOrder.setMerchantUserId(merchantUserId);
        queryOrder.setPayStatus(orderStatus);
        Orders waitPayOrder = getOne(Wrappers.lambdaQuery(queryOrder));

        return waitPayOrder;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String updateOrderPaid(String merchantOrderId, Integer paidAmount) {

        Orders paidOrder = new Orders();
        paidOrder.setPayStatus(PaymentStatus.SUCCESS.type);
        paidOrder.setAmount(paidAmount);
        paidOrder.setMerchantOrderId(merchantOrderId);
        update(Wrappers.lambdaUpdate(paidOrder));

        return queryMerchantReturnUrl(merchantOrderId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    String queryMerchantReturnUrl(String merchantOrderId) {

        Orders queryOrder = new Orders();
        queryOrder.setMerchantOrderId(merchantOrderId);
        Orders order = getOne(Wrappers.lambdaQuery(queryOrder));


        return order.getReturnUrl();
    }

    @Override
    public Orders queryOrderInfo(String merchantUserId, String merchantOrderId) {

        Orders orderInfo = new Orders();
        orderInfo.setMerchantOrderId(merchantOrderId);
        orderInfo.setMerchantUserId(merchantUserId);
        return getOne(Wrappers.lambdaQuery(orderInfo));
    }
}
