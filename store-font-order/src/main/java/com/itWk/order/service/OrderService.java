package com.itWk.order.service;

import com.itWk.Utils.Result;
import com.itWk.param.OrderRequest;

public interface OrderService {
    /**
     * 订单生成方法 订单数据保存业务
     * @param orderRequest
     * @return
     */
    Result save(OrderRequest orderRequest);
}
