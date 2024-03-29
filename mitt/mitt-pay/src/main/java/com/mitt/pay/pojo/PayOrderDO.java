package com.mitt.pay.pojo;

import com.mitt.pay.config.PayConstant;
import com.mitt.pay.utils.IPUtil;
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
     * 微信订单
     */
    private String prepayId;
    /**
     * 商户订单号
     */
    private String outOrderNo;
    /**
     * 订单号
     */
    private String orderNo;
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
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 请求地址
     */
    private String reqIP;
    /**
     * 附加数据
     */
    private String attach;

    public String getReqIP() {
        if (null == reqIP) {
            return IPUtil.getIpAddr();
        }
        return payType;
    }

    public String getPayType() {
        if (null == payType) {
            return PayConstant.PayEnum.NATIVE.toString();
        }
        return payType.toUpperCase();
    }

    public String getOutTradeNo() {
        if (null == outTradeNo) {
            outTradeNo = OrderNoUtil.getInstance().wxPayNO();
        }
        return outTradeNo;
    }
    public String getOutTradeNo(PayConstant.PayType type) {
        if(type== PayConstant.PayType.QQ){
            outTradeNo = OrderNoUtil.getInstance().qqPayNO();
        } else if (type== PayConstant.PayType.ZFB) {
            outTradeNo = OrderNoUtil.getInstance().aliPayNO();
        } else{
            outTradeNo = OrderNoUtil.getInstance().wxPayNO();
        }
        return outTradeNo;
    }

    public String getShopName() {
        if (null == shopName) {
            shopName = "在线支付";
        }
        return shopName;
    }
    public String getNonceStr() {
        if (null == nonceStr) {
            nonceStr = OrderNoUtil.getInstance().nonceStr();
        }
        return nonceStr;
    }

}
