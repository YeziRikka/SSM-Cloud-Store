package com.itWk.admin.service.impl;

import com.itWk.Clients.OrderClient;
import com.itWk.Utils.Result;
import com.itWk.admin.service.OrderService;
import com.itWk.param.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderClient orderClient;
    /**
     * 后台查询全部订单方法
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Result list(PageRequest pageRequest) {
        Result result = orderClient.list(pageRequest);
        log.info("OrderServiceImpl.list业务结束，结果:{}",result);
        return result;
    }
}
