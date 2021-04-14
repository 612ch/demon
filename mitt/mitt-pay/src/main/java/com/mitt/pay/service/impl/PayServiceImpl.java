package com.mitt.pay.service.impl;

import com.mitt.common.utils.Result;
import com.mitt.pay.config.WXConstant;
import com.mitt.pay.pojo.PayOrderDO;
import com.mitt.pay.pojo.PayOrderVO;
import com.mitt.pay.service.PayService;
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

    /**
     * 创建订单
     *
     * @param payOrderDO 支付订单传输数据
     * @return {@link Result}
     */
    @Override
    public Result createOrder(PayOrderDO payOrderDO) {
        PayOrderVO payOrderVO = null;
        WXPayConfigImpl config = new WXPayConfigImpl();
        try {
            WXPay wxpay = new WXPay(config, false, false);
            Map<String, String> reqData = new HashMap<String, String>();
            reqData.put("device_info", WXConstant.WXEnum.WEB.toString());
            reqData.put("body", payOrderDO.getShopName());
            reqData.put("out_trade_no", payOrderDO.getOutTradeNo());
            reqData.put("fee_type", WXConstant.WXEnum.CNY.toString());
            reqData.put("total_fee", payOrderDO.getMoney().toString());
            reqData.put("spbill_create_ip", "192.168.0.1");
            reqData.put("notify_url", WXConstant.NOTIFY_URL);
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
            if ("MWEB".equals(payOrderDO.getPayType())) {
                payOrderVO.setUrl(resp.get("mweb_url"));
            } else {
                payOrderVO.setUrl(resp.get("code_url"));
            }
        } catch (ClassCastException e) {
            log.warn("金额输入错误",e);
            // e.printStackTrace();
        } catch (Exception e) {
            log.warn("支付创建失败",e);
            // e.printStackTrace();
        }
        return Result.ok(payOrderVO);
    }
}
