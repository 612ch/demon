package com.mitt.pay.service.impl;

import com.mitt.common.utils.Result;
import com.mitt.pay.config.PayConstant;
import com.mitt.pay.pojo.PayOrderDO;
import com.mitt.pay.pojo.PayOrderVO;
import com.mitt.pay.pojo.QueryOrderVO;
import com.mitt.pay.qqpay.QQPayConfig;
import com.mitt.pay.service.PayService;
import com.mitt.pay.utils.HttpUtil;
import com.mitt.pay.utils.XmlUtil;
import com.mitt.pay.wxpay.WXPayConfigImpl;
import com.mitt.pay.wxpay.sdk.WXPay;
import com.mitt.pay.wxpay.sdk.WXPayConstants;
import com.mitt.pay.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author mitt
 * @className PayServiceImpl
 * @descriotion
 * @date 2021/4/14 18:39
 **/
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    @Override
    public Result createOrderByWX(PayOrderDO payOrderDO) {
        PayOrderVO payOrderVO = null;
        WXPayConfigImpl config = new WXPayConfigImpl();
        try {
            WXPay wxpay = new WXPay(config, false, false);
            Map<String, String> reqData = new HashMap<String, String>();
            reqData.put("device_info", PayConstant.PayEnum.WEB.toString());
            reqData.put("body", payOrderDO.getShopName());
            reqData.put("out_trade_no", payOrderDO.getOutTradeNo(PayConstant.PayType.WX));
            reqData.put("fee_type", PayConstant.PayEnum.CNY.toString());
            reqData.put("nonce_str", payOrderDO.getNonceStr());
            reqData.put("attach", payOrderDO.getAttach() + "");
            reqData.put("total_fee", payOrderDO.getMoney().toString());
            reqData.put("spbill_create_ip", payOrderDO.getReqIP());
            reqData.put("notify_url", PayConstant.NOTIFY_URL);
            reqData.put("trade_type", payOrderDO.getPayType());  // 此处指定为扫码支付
            reqData.put("product_id", UUID.randomUUID().toString());// 商品id trade_type为NATIVE必传
            String sign = WXPayUtil.generateSignature(reqData, config.getKey(), WXPayConstants.SignType.HMACSHA256);
            reqData.put("sign", sign);// 签名
            // 发送请求不需要验证签名 接口回调需要验证签名
            // boolean flog = WXPayUtil.isSignatureValid(reqData, config.getKey(), WXPayConstants.SignType.HMACSHA256);// 验证签名
            Map<String, String> resp = wxpay.unifiedOrder(reqData);
            payOrderVO = new PayOrderVO();
            payOrderVO.setOutTradeNo(payOrderDO.getOutTradeNo());
            payOrderVO.setPrepayId(resp.get("prepay_id"));
            if (PayConstant.PayEnum.MWEB.toString().equals(payOrderDO.getPayType())) {
                payOrderVO.setUrl(resp.get("mweb_url"));
            } else {
                payOrderVO.setUrl(resp.get("code_url"));
            }
        } catch (ClassCastException e) {
            log.warn("金额输入错误", e);
        } catch (Exception e) {
            log.warn("支付创建失败", e);
        }
        return Result.ok(payOrderVO);
    }

    @Override
    public Result queryOrderByWX(PayOrderDO payOrderDO) {
        QueryOrderVO queryOrderVO = null;
        WXPayConfigImpl config = new WXPayConfigImpl();
        try {
            WXPay wxpay = new WXPay(config, false, false);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", payOrderDO.getOutOrderNo());
            Map<String, String> resp = wxpay.orderQuery(data);
            if (PayConstant.PayEnum.SUCCESS.toString().equals(resp.get("result_code"))) {
                queryOrderVO = new QueryOrderVO();
                queryOrderVO.setOutTradeNo(resp.get("out_trade_no"));
                queryOrderVO.setTradeState(resp.get("trade_state"));
                queryOrderVO.setTradeStateDesc(resp.get("trade_state_desc"));
                queryOrderVO.setPayTime(resp.get("time_end"));
                queryOrderVO.setTotalFee(resp.get("total_fee"));
                return Result.ok(queryOrderVO);
            } else {
                return Result.ok(resp.get("err_code_des"));
            }
        } catch (Exception e) {
            log.warn("订单查询失败", e);
        }
        return Result.ok(queryOrderVO);
    }

    @Override
    public Result closeOrderByWX(PayOrderDO payOrderDO) {
        WXPayConfigImpl config = new WXPayConfigImpl();
        try {
            WXPay wxpay = new WXPay(config, false, false);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", payOrderDO.getOutOrderNo());
            Map<String, String> resp = wxpay.closeOrder(data);
            if (PayConstant.PayEnum.SUCCESS.toString().equals(resp.get("result_code"))) {
                return Result.msg("关闭订单成功");
            } else {
                return Result.fail(400, "关闭失败");
            }
        } catch (Exception e) {
            log.warn("微信订单关闭失败", e);
        }
        return Result.fail("关闭失败");
    }

    @Override
    public Result createOrderByQQ(PayOrderDO payOrderDO) {
        PayOrderVO payOrderVO = null;
        QQPayConfig config = new QQPayConfig();
        try {
            Map<String, String> reqData = new HashMap<String, String>();
            reqData.put("body", payOrderDO.getShopName());
            reqData.put("nonce_str", payOrderDO.getNonceStr());
            reqData.put("out_trade_no", payOrderDO.getOutTradeNo(PayConstant.PayType.QQ));
            reqData.put("fee_type", PayConstant.PayEnum.CNY.toString());
            reqData.put("attach", payOrderDO.getAttach() + "");
            reqData.put("total_fee", payOrderDO.getMoney().toString());
            reqData.put("spbill_create_ip", payOrderDO.getReqIP());
            reqData.put("mch_id", config.getAppId());
            reqData.put("notify_url", PayConstant.NOTIFY_URL);
            reqData.put("trade_type", payOrderDO.getPayType());  // 此处指定为扫码支付
            String sign = WXPayUtil.generateSignature(reqData, config.getAppKey(), WXPayConstants.SignType.MD5);
            reqData.put("sign", sign);// 签名
            // 发送请求不需要验证签名 接口回调需要验证签名
            // boolean flog = WXPayUtil.isSignatureValid(reqData, config.getKey(), WXPayConstants.SignType.HMACSHA256);// 验证签名
            String reqXml = XmlUtil.mapToXml(reqData);
            String resXml = HttpUtil.postXML(config.getDomainApiUnified(), reqXml);
            Map<String, String> respData = XmlUtil.xmlToMap(resXml);
            payOrderVO = new PayOrderVO();
            payOrderVO.setOutTradeNo(payOrderDO.getOutTradeNo());
            payOrderVO.setPrepayId(respData.get("prepay_id"));
            payOrderVO.setUrl(respData.get("code_url"));
        } catch (ClassCastException e) {
            log.warn("金额输入错误", e);
        } catch (Exception e) {
            log.warn("支付创建失败", e);
        }
        return Result.ok(payOrderVO);
    }

    @Override
    public Result queryOrderByQQ(PayOrderDO payOrderDO) {
        QueryOrderVO queryOrderVO=null;
        QQPayConfig config = new QQPayConfig();
        try {
            Map<String, String> reqData = new HashMap<String, String>();
            reqData.put("body", payOrderDO.getShopName());
            reqData.put("nonce_str", payOrderDO.getNonceStr());
            reqData.put("out_trade_no", payOrderDO.getOutOrderNo());
            reqData.put("mch_id", config.getAppId());
            String sign = WXPayUtil.generateSignature(reqData, config.getAppKey(), WXPayConstants.SignType.MD5);
            reqData.put("sign", sign);// 签名
            // 发送请求不需要验证签名 接口回调需要验证签名
            // boolean flog = WXPayUtil.isSignatureValid(reqData, config.getKey(), WXPayConstants.SignType.HMACSHA256);// 验证签名
            String reqXml = XmlUtil.mapToXml(reqData);
            String resXml = HttpUtil.postXML(config.getDomainApiQuery(), reqXml);
            if (null != resXml) {
                Map<String, String> respData = XmlUtil.xmlToMap(resXml);
                if (PayConstant.PayEnum.SUCCESS.toString().equals(respData.get("result_code"))) {
                    queryOrderVO = new QueryOrderVO();
                    queryOrderVO.setOutTradeNo(respData.get("out_trade_no"));
                    queryOrderVO.setTradeState(respData.get("trade_state"));
                     queryOrderVO.setTradeStateDesc(respData.get("trade_state_desc"));
                     queryOrderVO.setPayTime(respData.get("time_end"));
                    queryOrderVO.setTotalFee(respData.get("total_fee"));
                    return Result.ok(queryOrderVO);
                } else {
                    return Result.fail(400, respData.get("err_code_des"));
                }
            }
        } catch (ClassCastException e) {
            log.warn("订单查询失败", e);
        } catch (Exception e) {
            log.warn("订单查询失败", e);
        }

        return Result.ok(queryOrderVO);
    }

    @Override
    public Result closeOrderByQQ(PayOrderDO payOrderDO) {
        QQPayConfig config = new QQPayConfig();
        try {
            Map<String, String> reqData = new HashMap<String, String>();
            reqData.put("body", payOrderDO.getShopName());
            reqData.put("nonce_str", payOrderDO.getNonceStr());
            reqData.put("out_trade_no", payOrderDO.getOutOrderNo());
            reqData.put("mch_id", config.getAppId());
            String sign = WXPayUtil.generateSignature(reqData, config.getAppKey(), WXPayConstants.SignType.MD5);
            reqData.put("sign", sign);// 签名
            // 发送请求不需要验证签名 接口回调需要验证签名
            // boolean flog = WXPayUtil.isSignatureValid(reqData, config.getKey(), WXPayConstants.SignType.HMACSHA256);// 验证签名
            String reqXml = XmlUtil.mapToXml(reqData);
            String resXml = HttpUtil.postXML(config.getDomainApiClose(), reqXml);
            if (null != resXml) {
                Map<String, String> respData = XmlUtil.xmlToMap(resXml);
                if (PayConstant.PayEnum.SUCCESS.toString().equals(respData.get("result_code"))) {
                    return Result.msg("订单关闭成功");
                } else {
                    return Result.fail(400, respData.get("err_code_des"));
                }
            }
        } catch (ClassCastException e) {
            log.warn("订单关闭成功", e);
        } catch (Exception e) {
            log.warn("订单关闭成功", e);
        }

        return null;
    }

    @Override
    public Result createOrderByAli(PayOrderDO payOrderDO) {

        return Result.ok();
    }

}
