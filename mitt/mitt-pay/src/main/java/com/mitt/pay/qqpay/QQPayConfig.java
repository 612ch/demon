package com.mitt.pay.qqpay;

import com.mitt.pay.config.QQConstant;

/**
 * @ClassName QQPayConfig
 * @Descriotion
 * @Author mitt
 * @Date 2021/4/15 1:01
 **/

public class QQPayConfig {

    public String getAppId() {
        return QQConstant.APP_ID;
    }

    public String getAppKey() {
        return QQConstant.APP_KEY;
    }

    public String getDomainApiUnified() {
        return QQConstant.DOMAIN_API_UNIFIED;
    }
    public String getDomainApiQuery() {
        return QQConstant.DOMAIN_API_QUERY;
    }
    public String getDomainApiClose() {
        return QQConstant.DOMAIN_API_CLOSE;
    }
    public String getDomainApiRefund() {
        return QQConstant.DOMAIN_API_REFUND;
    }
    public String getDomainApiRefundQuery() {
        return QQConstant.DOMAIN_API_REFUND_QUERY;
    }
}
