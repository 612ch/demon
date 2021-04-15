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
    Result createOrderByWX(PayOrderDO payOrderDO);
    Result queryOrderByWX(PayOrderDO payOrderDO);
    Result closeOrderByWX(PayOrderDO payOrderDO);
    Result createOrderByQQ(PayOrderDO payOrderDO);
    Result queryOrderByQQ(PayOrderDO payOrderDO);
    Result closeOrderByQQ(PayOrderDO payOrderDO);
    Result createOrderByAli(PayOrderDO payOrderDO);
}
