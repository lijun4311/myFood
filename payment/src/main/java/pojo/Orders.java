package pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    /**
     * 订单主键
     */

    @TableId
    private String id;

    /**
     * 商户订单号
     */

    private String merchantOrderId;

    /**
     * 商户方的发起用户的用户主键id
     */

    private String merchantUserId;

    /**
     * 实际支付总金额（包含商户所支付的订单费邮费总额）
     */
    private Integer amount;

    /**
     * 支付方式
     */

    private Integer payMethod;

    /**
     * 支付状态 10：未支付 20：已支付 30：支付失败 40：已退款
     */

    private Integer payStatus;

    /**
     * 从哪一端来的，比如从天天吃货这门实战过来的
     */

    private String comeFrom;

    /**
     * 支付成功后的通知地址，这个是开发者那一段的，不是第三方支付通知的地址
     */

    private String returnUrl;

    /**
     * 逻辑删除状态;1: 删除 0:未删除
     */
    private Integer isDelete;

    /**
     * 创建时间（成交时间）
     */

    private Date createdTime;


}