package com.mitt.pay.pojo;

import lombok.Data;

/**
 * @author mitt
 * @className QueryOrderVO
 * @descriotion
 * @date 2021/4/15 15:18
 **/
@Data
public class QueryOrderVO {
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 订单状态
     */
    private String tradeState;
    /**
     * 订单状态描述
     */
    private String tradeStateDesc;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 总金额
     */
    private String totalFee;

}
