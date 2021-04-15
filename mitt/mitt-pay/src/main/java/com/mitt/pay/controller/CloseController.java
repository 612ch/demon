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
 * @className CloseController
 * @descriotion
 * @date 2021/4/15 16:32
 **/
@RestController
@RequestMapping("close")
public class CloseController {
    @Autowired
    PayService payService;

    @RequestMapping("wx")
    public Result queryOrderByWx(PayOrderDO payOrderDO){
        if(!CheckUtil.notEmpty(payOrderDO.getOutOrderNo())){
            return Result.fail(400, "商户订单号outOrderNo不能为空");
        }
        return payService.closeOrderByWX(payOrderDO);
    }

    @RequestMapping("qq")
    public Result queryOrderByQq(PayOrderDO payOrderDO){
        if(!CheckUtil.notEmpty(payOrderDO.getOutOrderNo())){
            return Result.fail(400, "商户订单号outOrderNo不能为空");
        }
        return payService.closeOrderByQQ(payOrderDO);
    }

}
