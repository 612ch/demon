package com.mitt.pay.config;

/**
 * @author mitt
 * @className WXConstant
 * @descriotion 微信支付常量
 * @date 2021/3/16 14:50
 **/
public class WXConstant {
    /**
     * APPID
     */
    public static final String APP_ID = "wxb72dff6c1a123456";
    /**
     * MCHID
     */
    public static final String MCH_ID = "1531053901";
    /**
     * API key
     */
    public static final String API_KEY = "132456";
    /**
     * 回调地址
     */
    public static final String NOTIFY_URL = "https://www.612ch.com/";

    public enum WXEnum{
        WEB,CNY
    }

}
