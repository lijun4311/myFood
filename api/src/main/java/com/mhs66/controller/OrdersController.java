package com.mhs66.controller;


import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;

import com.mhs66.consts.RedisPrefix;
import com.mhs66.enums.PaymentStatus;
import com.mhs66.pojo.OrderStatus;
import com.mhs66.pojo.bo.ShopcartBO;
import com.mhs66.pojo.bo.SubmitOrderBO;
import com.mhs66.pojo.vo.MerchantOrdersVO;
import com.mhs66.pojo.vo.OrderVO;
import com.mhs66.service.IOrderStatusService;
import com.mhs66.service.IOrdersService;
import com.mhs66.utils.CookieUtil;
import com.mhs66.utils.JsonUtil;
import com.mhs66.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
@AllArgsConstructor
public class OrdersController extends BaseController {


    private final IOrdersService ordersService;

    private final RedisOperator redisOperator;

    private final IOrderStatusService iOrderStatusService;


    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    @BusinessObjectNotEmpty(strict = true)
    public WebResult create(
            @Validated @RequestBody SubmitOrderBO submitOrderBO, BindingResult result
    ) {

        String key = RedisPrefix.SHOPCART + ":" + submitOrderBO.getUserId();
        String shopcartJson = redisOperator.get(key);
        if (StringUtils.isBlank(shopcartJson)) {
            return WebResult.errorMsg("购物数据不正确");
        }
        List<ShopcartBO> shopcartList = JsonUtil.stringToObj(shopcartJson, List.class, ShopcartBO.class);
        // 1. 创建订单
        OrderVO orderVO = ordersService.createOrder(submitOrderBO, shopcartList);
        String orderId = orderVO.getOrderId();

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        shopcartList.removeAll(orderVO.getToBeRemovedShopcatdList());
        String value = JsonUtil.objToString(shopcartList);
        redisOperator.set(key, value);
        CookieUtil.setCookie(request, response, key, value);

        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        // TODO 整合支付中心

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
