package service;


import com.mhs66.service.BaseService;
import pojo.Orders;
import pojo.bo.MerchantOrdersBO;

public interface PaymentOrderService  extends BaseService<Orders> {

	/**
	 * @Description: 创建支付中心的订单
	 */
	public boolean createPaymentOrder(MerchantOrdersBO merchantOrdersBO);

	/**
	 * @Description: 查询未支付订单
	 */
	public Orders queryOrderByStatus(String merchantUserId, String merchantOrderId, Integer orderStatus);

	/**
	 * @Description: 修改订单状态为已支付
	 */
	public String updateOrderPaid(String merchantOrderId, Integer paidAmount);

	/**
	 * @Description: 查询订单信息
	 */
	public Orders queryOrderInfo(String merchantUserId, String merchantOrderId);
}

