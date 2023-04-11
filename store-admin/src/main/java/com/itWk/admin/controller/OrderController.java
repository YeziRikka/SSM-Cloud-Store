package com.itWk.admin.controller;

import com.itWk.Utils.Result;
import com.itWk.admin.service.OrderService;
import com.itWk.param.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单的控制类
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public Result list(PageRequest pageRequest){
        return orderService.list(pageRequest);
    }



}
