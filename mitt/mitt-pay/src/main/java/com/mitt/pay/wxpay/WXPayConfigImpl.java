package com.mitt.pay.wxpay;

import com.mitt.pay.config.WXConstant;
import com.mitt.pay.wxpay.sdk.IWXPayDomain;
import com.mitt.pay.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 微信配置
 *
 * @author mitt
 * @date 2021/03/16
 */
public class WXPayConfigImpl implements WXPayConfig {

    private static byte[] certData;

    @Override
    public String getAppID() {
        return WXConstant.APP_ID;
    }

    @Override
    public String getMchID() {
        return WXConstant.MCH_ID;
    }

    @Override
    public String getKey() {
        return WXConstant.API_KEY;
    }

    @Override
    public InputStream getCertStream() {
        if (null == this.certData) {
            return null;
        }
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 6 * 1000;
    }
    @Override
    public int getHttpReadTimeoutMs() {
        return 8 * 1000;
    }
    @Override
    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainImpl.instance();
    }
    @Override
    public boolean shouldAutoReport() {
        return true;
    }
    @Override
    public int getReportWorkerNum() {
        return 6;
    }
    @Override
    public int getReportQueueMaxSize() {
        return 10000;
    }
    @Override
    public int getReportBatchSize() {
        return 10;
    }
}
