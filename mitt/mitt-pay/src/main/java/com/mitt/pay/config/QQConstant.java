package com.mitt.pay.config;

/**
 * @author mitt
 * @className QQConstant
 * @descriotion QQ支付常量
 * @date 2021/3/16 17:24
 **/
public class QQConstant {
    /**
     * APPID
     */
    public static final String APP_ID = "1530053311";
    /**
     * APIKEY
     */
    public static final String APP_KEY = "123456";

    /**
     * 支付域名 下单
     */
    public static final String DOMAIN_API_UNIFIED = "https://qpay.qq.com/cgi-bin/pay/qpay_unified_order.cgi";
    /**
     * 支付域名 查询
     */
    public static final String DOMAIN_API_QUERY = "https://qpay.qq.com/cgi-bin/pay/qpay_order_query.cgi";
    /**
     * 支付域名 关闭
     */
    public static final String DOMAIN_API_CLOSE = "https://qpay.qq.com/cgi-bin/pay/qpay_close_order.cgi";
    /**
     * 支付域名 申请退款
     */
    public static final String DOMAIN_API_REFUND = "https://api.qpay.qq.com/cgi-bin/pay/qpay_refund.cgi";
    /**
     * 支付域名 退款查询
     */
    public static final String DOMAIN_API_REFUND_QUERY = "https://qpay.qq.com/cgi-bin/pay/qpay_refund_query.cgi";
}
