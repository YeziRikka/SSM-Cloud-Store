package com.itWk.cart.controller;

import com.itWk.POJO.Cart;
import com.itWk.Utils.Result;
import com.itWk.cart.service.CartService;
import com.itWk.param.CartListRequest;
import com.itWk.param.CartRequest;
import com.itWk.param.CartSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车控制类
 */
@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/save")
    public Result save(@RequestBody @Validated CartSaveRequest cartSaveRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.fail("添加失败");
        }
        return cartService.save(cartSaveRequest);
    }

    @PostMapping("/list")
    public Result list(@RequestBody @Validated CartListRequest cartListRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.fail("购物车查询失败");
        }
        return  cartService.list(cartListRequest.getUserId());
    }

    @PostMapping("/update")
    public Result update(@RequestBody Cart cart){
        return cartService.update(cart);
    }

    @PostMapping("/remove")
    public Result remove(@RequestBody Cart cart){
        return cartService.remove(cart);
    }

    @PostMapping("/remove/check")
    public Result removeCheck(@RequestBody Integer productId){
        return  cartService.check(productId);
    }

}
