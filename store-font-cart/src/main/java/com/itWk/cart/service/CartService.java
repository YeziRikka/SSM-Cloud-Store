package com.itWk.cart.service;

import com.itWk.POJO.Cart;
import com.itWk.Utils.Result;
import com.itWk.param.CartRequest;
import com.itWk.param.CartSaveRequest;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface CartService {
    /**
     * 购物车添加功能
     * @param cartSaveRequest
     * @return
     */
    @PostMapping("/save")
    Result save(CartSaveRequest cartSaveRequest);

    /**
     * 查询购物车数据 返回集合
     * @param userId
     * @return
     */
    Result list(Integer userId);

    /**
     * 更新购物车
     * @param cart
     * @return
     */
    Result update(Cart cart);

    /**
     * 删除
     * @param
     * @return
     */
    Result remove(Cart cart);

    /**
     * 清空购物车
     * @param cardIds
     */
    void clearIds(List<Integer> cardIds);
}
