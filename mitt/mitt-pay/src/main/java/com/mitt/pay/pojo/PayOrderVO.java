package com.mitt.pay.pojo;

import lombok.Data;

/**
 * @author mitt
 * @className PayOrderVO
 * @descriotion 订单支付返回VO
 * @date 2021/4/14 19:37
 **/
@Data
public class PayOrderVO {
    /**
     * 支付URL
     */
    private String url;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 微信返回订单
     */
    private String prepayId;
}
