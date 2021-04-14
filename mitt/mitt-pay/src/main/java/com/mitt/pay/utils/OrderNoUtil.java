package com.mitt.pay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author mitt
 * @className OrderNoUtil
 * @descriotion 生成订单号工具类
 * @date 2021/3/16 15:54
 **/
public class OrderNoUtil {
    /**
     * 微信支付订单号标识头
     */
    private static final String WX_ORDER_PRE = "WX";
    /**
     * QQ钱包订单号标识头
     */
    private static final String QQ_ORDER_PRE = "QQ";//连连支付
    /**
     * 支付宝订单号标识头
     */
    private static final String ALIPAY_ORDER_PRE = "ZFB";//支付宝
    /**
     * 日期格式
     */
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmssS");

    private OrderNoUtil() {
    }

    private static OrderNoUtil instance = null;

    /**
     * 获得实例 单例模式
     *
     * @return {@link OrderNoUtil}
     */
    public static OrderNoUtil getInstance() {
        if (instance == null) {
            synchronized (OrderNoUtil.class) {
                if (instance == null) {
                    instance = new OrderNoUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 微信生成订单号
     *
     * @return {@link String}
     */
    public String WXPayNO() {
        String str = null;
        try {
            String uuid = UUID.randomUUID().toString();
            str =  WX_ORDER_PRE + SDF.format(new Date()) + uuid.substring(0, 8);
        } catch (Exception e) {
            return null;
        }
        return str;
    }
    /**
     * QQ钱包生成订单号
     *
     * @return {@link String}
     */
    public String QQPayNO() {
        String str = null;
        try {
            String uuid = UUID.randomUUID().toString();
            str = QQ_ORDER_PRE + SDF.format(new Date()) + uuid.substring(0, 8);
        } catch (Exception e) {
            return null;
        }
        return str;
    }
    /**
     * 支付宝生成订单号
     *
     * @return {@link String}
     */
    public String AlipayNO() {
        String str = null;
        try {
            String uuid = UUID.randomUUID().toString();
            str = ALIPAY_ORDER_PRE + SDF.format(new Date()) + uuid.substring(0, 8);
        } catch (Exception e) {
            return null;
        }
        return str;
    }

}
