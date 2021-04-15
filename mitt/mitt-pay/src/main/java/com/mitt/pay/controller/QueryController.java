package com.mitt.pay.controller;

import com.mitt.common.utils.CheckUtil;
import com.mitt.common.utils.Result;
import com.mitt.pay.pojo.PayOrderDO;
import com.mitt.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mitt
 * @className QueryController
 * @descriotion 订单查询
 * @date 2021/4/15 15:07
 **/
@RestController
@RequestMapping("query")
public class QueryController {
    @Autowired
    PayService payService;

    @RequestMapping("wx")
    public Result queryOrderByWx(PayOrderDO payOrderDO){
        if(!CheckUtil.notEmpty(payOrderDO.getOutOrderNo())){
            return Result.fail(400, "商户订单号outOrderNo不能同时为空");
        }
        return payService.queryOrderByWX(payOrderDO);
    }
    @RequestMapping("qq")
    public Result queryOrderByQq(PayOrderDO payOrderDO){
        if(!CheckUtil.notEmpty(payOrderDO.getOutOrderNo())){
            return Result.fail(400, "商户订单号outOrderNo不能同时为空");
        }
        return payService.queryOrderByQQ(payOrderDO);
    }
}
