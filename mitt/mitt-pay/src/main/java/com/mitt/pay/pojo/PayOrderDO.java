package com.mitt.pay.pojo;

import com.mitt.pay.utils.OrderNoUtil;
import lombok.Data;

/**
 * @author mitt
 * @className PayOrderDO
 * @descriotion
 * @date 2021/4/14 19:21
 **/
@Data
public class PayOrderDO {


    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 通知地址
     */
    private String notifyUrl;
    /**
     * 异步跳转地址
     */
    private String returnUrl;
    /**
     * 商品名称
     */
    private String shopName;
    /**
     * 商品金额
     */
    private Integer money;
    /**
     * 网站名称
     */
    private String siteName;
    /**
     * 签名字符串
     */
    private String sign;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 支付类型 JSAPI, NATIVE, APP, MWEB
     */
    private String payType;


    public String getPayType() {
        if (null == payType) {
            payType = "NATIVE";
        }
        return payType.toUpperCase();
    }
    public String getOutTradeNo() {
        if (null == outTradeNo) {
            outTradeNo = OrderNoUtil.getInstance().WXPayNO();
        }
        return outTradeNo;
    }

    public String getShopName() {
        if (null == shopName) {
            shopName = "在线支付";
        }
        return shopName;
    }

}
