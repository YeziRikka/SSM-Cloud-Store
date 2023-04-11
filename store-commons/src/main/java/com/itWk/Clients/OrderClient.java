package com.itWk.Clients;

import com.itWk.Utils.Result;
import com.itWk.param.PageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 订单客户端
 */
@FeignClient("order-service")
public interface OrderClient {

    @PostMapping("/order/remove/check")
    Result check(@RequestBody Integer productId);

    @PostMapping("/order/admin/list")
    Result list(@RequestBody PageRequest pageRequest);
}
