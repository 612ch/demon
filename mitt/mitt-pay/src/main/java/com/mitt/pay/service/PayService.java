package com.mitt.pay.service;

import com.mitt.common.utils.Result;
import com.mitt.pay.pojo.PayOrderDO;

/**
 * @author mitt
 * @className PayService
 * @descriotion
 * @date 2021/4/14 18:39
 **/
public interface PayService {
    Result createOrder(PayOrderDO payOrderDO);
}
