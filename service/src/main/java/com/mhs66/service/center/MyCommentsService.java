package com.mhs66.service.center;



import com.mhs66.pojo.ItemsComments;
import com.mhs66.pojo.OrderItems;
import com.mhs66.pojo.bo.center.OrderItemsCommentBO;
import com.mhs66.pojo.vo.PageVo;
import com.mhs66.service.BaseService;

import java.util.List;

public interface MyCommentsService extends BaseService<ItemsComments> {

    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    public List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);


    /**
     * 我的评价查询 分页
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public PageVo queryMyComments(String userId, Integer page, Integer pageSize);
}
