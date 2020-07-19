package com.mhs66.controller.center;


import com.mhs66.WebResult;
import com.mhs66.annotation.BusinessObjectNotEmpty;
import com.mhs66.controller.BaseController;
import com.mhs66.enums.BusinessStatus;
import com.mhs66.pojo.OrderItems;
import com.mhs66.pojo.Orders;
import com.mhs66.pojo.bo.PageBo;
import com.mhs66.pojo.bo.center.OrderItemsCommentBO;
import com.mhs66.pojo.vo.PageVo;
import com.mhs66.service.center.MyCommentsService;
import com.mhs66.service.center.MyOrdersService;
import com.mhs66.utils.IBeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户中心评价模块", tags = {"用户中心评价模块相关接口"})
@RestController
@RequestMapping("mycomments")
@AllArgsConstructor
public class MyCommentsController extends BaseController {


    private final MyCommentsService myCommentsService;
    private final MyOrdersService myOrdersService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/pending")
    public WebResult pending(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) {

        // 判断用户和订单是否关联
        Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if (IBeanUtil.isRequired(orders)) {
            return WebResult.errorMsg("订单不存在");
        }
        // 判断该笔订单是否已经评价过，评价过了就不再继续

        if (orders.getIsComment().equals(BusinessStatus.YES.type)) {
            return WebResult.errorMsg("该笔订单已经评价");
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return WebResult.okData(list);
    }


    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public WebResult saveList(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList) {

        System.out.println(commentList);

        // 判断用户和订单是否关联
        Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if (IBeanUtil.isRequired(orders)) {
            return WebResult.errorMsg("订单不存在");
        }
        // 判断评论内容list不能为空
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return WebResult.errorMsg("评论内容不能为空！");
        }

        myCommentsService.saveComments(orderId, userId, commentList);
        return WebResult.ok();
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("/query")
    @BusinessObjectNotEmpty
    public WebResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            PageBo page) {


        PageVo vo = myCommentsService.queryMyComments(userId,
                page.getPage(),
                page.getPageSize());

        return WebResult.okData(vo);
    }


}
