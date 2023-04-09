package com.itWk.order.controller;

import com.itWk.Utils.Result;
import com.itWk.order.service.OrderService;
import com.itWk.param.CartListRequest;
import com.itWk.param.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/save")
    public Result save(@RequestBody OrderRequest orderRequest){
        return orderService.save(orderRequest);
    }

    @PostMapping("/list")
    public Result lists(@RequestBody @Validated CartListRequest cartListRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.fail("参数异常,查询失败");
        }
        return orderService.lists(cartListRequest.getUserId());
    }
}
