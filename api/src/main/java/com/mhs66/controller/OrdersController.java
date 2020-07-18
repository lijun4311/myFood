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
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();

        String payReturnUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        // 为了方便测试购买，所以所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");

        HttpEntity<MerchantOrdersVO> entity =
                new HttpEntity<>(merchantOrdersVO, headers);
        // 支付中心的调用地址
        String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
        ResponseEntity<WebResult> responseEntity =
                restTemplate.postForEntity(paymentUrl,
                        entity,
                        WebResult.class);
        WebResult paymentResult = responseEntity.getBody();
        if (paymentResult.getStatus() != 200) {
            log.error("发送错误：{}", paymentResult.getMsg());
            return WebResult.errorMsg("支付中心订单创建失败，请联系管理员！");
        }

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
