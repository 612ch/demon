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
 * @className PayController
 * @descriotion
 * @date 2021/4/14 18:39
 **/
@RestController
@RequestMapping("pay")
public class PayController {
    @Autowired
    PayService payService;

    @RequestMapping("wx")
    public Result createOrderNativeByWx(PayOrderDO payOrderDO){
        if(!CheckUtil.notEmpty(payOrderDO.getMoney())){
           return Result.fail(400, "金额不能为空");
        }
        return payService.createOrderByWX(payOrderDO);
    }

    @RequestMapping("qq")
    public Result createOrderNativeByQq(PayOrderDO payOrderDO){
        if(!CheckUtil.notEmpty(payOrderDO.getMoney())){
           return Result.fail(400, "金额不能为空");
        }
        return payService.createOrderByQQ(payOrderDO);
    }

    @RequestMapping("ali")
    public Result createOrderNativeByAli(PayOrderDO payOrderDO){
        if(!CheckUtil.notEmpty(payOrderDO.getMoney())){
           return Result.fail(400, "金额不能为空");
        }
        return payService.createOrderByAli(payOrderDO);
    }

}
