package com.itWk.Clients;

import com.itWk.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 购物车调用的客户端
 */
@FeignClient("cart-service")
public interface CartClient {

    @PostMapping("/cart/remove/check")
    Result check(@RequestBody Integer productId);
}
