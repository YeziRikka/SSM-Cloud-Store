package com.itWk.admin.service;


import com.itWk.Utils.Result;
import com.itWk.param.PageRequest;

public interface OrderService {
    /**
     * 后台查询全部订单方法
     * @param pageRequest
     * @return
     */
    Result list(PageRequest pageRequest);
}
