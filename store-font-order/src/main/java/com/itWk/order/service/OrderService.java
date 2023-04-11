package com.itWk.order.service;

import com.itWk.Utils.Result;
import com.itWk.param.OrderRequest;
import com.itWk.param.PageRequest;

public interface OrderService {
    /**
     * 订单生成方法 订单数据保存业务
     * @param orderRequest
     * @return
     */
    Result save(OrderRequest orderRequest);

    /**
     * 分组查询订单数据
     * @param userId
     * @return
     */
    Result lists(Integer userId);

    /**
     * 检查订单中是否有商品引用
     * @param productId
     * @return
     */
    Result check(Integer productId);

    /**
     * 后台管理查询全部订单数据方法
     * @param pageRequest
     * @return
     */
    Result adminList(PageRequest pageRequest);
}
