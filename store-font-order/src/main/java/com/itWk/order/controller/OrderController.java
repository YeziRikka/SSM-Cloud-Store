package com.itWk.order.controller;

import com.itWk.Utils.Result;
import com.itWk.order.service.OrderService;
import com.itWk.param.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单服务控制类
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    public Result save(@RequestBody OrderRequest orderRequest){
        return orderService.save(orderRequest);
    }
}