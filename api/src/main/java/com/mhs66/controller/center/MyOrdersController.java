package com.mhs66.controller.center;


import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.controller.BaseController;
import com.mhs66.pojo.Orders;
import com.mhs66.pojo.bo.PageBo;
import com.mhs66.pojo.vo.OrderStatusCountsVO;
import com.mhs66.pojo.vo.PageVo;
import com.mhs66.service.center.MyOrdersService;
import com.mhs66.utils.IBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户中心我的订单", tags = {"用户中心我的订单相关接口"})
@RestController
@RequestMapping("myorders")
@AllArgsConstructor
public class MyOrdersController extends BaseController {

    private final MyOrdersService myOrdersService;

    @ApiOperation(value = "获得订单状态数概况", notes = "获得订单状态数概况", httpMethod = "POST")
    @PostMapping("/statusCounts")
    @BusinessObjectNotEmpty
    public WebResult statusCounts(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) {


        OrderStatusCountsVO result = myOrdersService.getOrderStatusCounts(userId);

        return WebResult.okData(result);
    }

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")

    public WebResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @BusinessObjectNotEmpty  @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false)
            @RequestParam Integer orderStatus,
            PageBo bo) {


        PageVo vo = myOrdersService.queryMyOrders(userId,
                orderStatus,
                bo.getPage(),
                bo.getPageSize());

        return WebResult.okData(vo);
    }


    // 商家发货没有后端，所以这个接口仅仅只是用于模拟
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    @BusinessObjectNotEmpty
    public WebResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) {

        if (StringUtils.isBlank(orderId)) {
            return WebResult.errorMsg("订单ID不能为空");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return WebResult.ok();
    }


    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public WebResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) throws Exception {

        Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if (IBeanUtil.isRequired(orders)) {
            return WebResult.errorMsg("订单不存在");
        }

        boolean res = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!res) {
            return WebResult.errorMsg("订单确认收货失败！");
        }

        return WebResult.ok();
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public WebResult delete(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) throws Exception {

        Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if (IBeanUtil.isRequired(orders)) {
            return WebResult.errorMsg("订单不存在");
        }

        boolean res = myOrdersService.deleteOrder(userId, orderId);
        if (!res) {
            return WebResult.errorMsg("订单删除失败！");
        }

        return WebResult.ok();
    }


    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    @BusinessObjectNotEmpty
    public WebResult trend(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            PageBo bo) {


        PageVo vo = myOrdersService.getOrdersTrend(userId,
                bo.getPage(),
                bo.getPageSize());

        return WebResult.okData(vo);
    }

}
