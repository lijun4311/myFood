package com.mhs66.controller;


import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;

import com.mhs66.enums.PaymentStatus;
import com.mhs66.pojo.OrderStatus;
import com.mhs66.pojo.bo.SubmitOrderBO;
import com.mhs66.pojo.vo.MerchantOrdersVO;
import com.mhs66.pojo.vo.OrderVO;
import com.mhs66.service.IOrderStatusService;
import com.mhs66.service.IOrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
@AllArgsConstructor
public class OrdersController extends BaseController {


    private final IOrdersService ordersService;

    @Autowired
    private final RestTemplate restTemplate;

    private final IOrderStatusService iOrderStatusService;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    @BusinessObjectNotEmpty(strict = true)
    public WebResult create(
            @Validated @RequestBody SubmitOrderBO submitOrderBO, BindingResult result
    ) {


        // 1. 创建订单
        OrderVO orderVO = ordersService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        // TODO 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        return WebResult.okData(orderId);
    }

    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        ordersService.updateOrderStatus(merchantOrderId, PaymentStatus.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("getPaidOrderInfo")
    public WebResult getPaidOrderInfo(String orderId) {
        OrderStatus orderStatus = iOrderStatusService.getById(orderId);
        return WebResult.okData(orderStatus);
    }
}
