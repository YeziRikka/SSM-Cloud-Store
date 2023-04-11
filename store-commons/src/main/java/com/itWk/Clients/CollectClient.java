package com.itWk.Clients;

import com.itWk.Utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 商品收藏调用客户端
 */
@FeignClient("collect-service")
public interface CollectClient {

    @PostMapping("/collect/remove/product")
    Result remove(@RequestBody Integer productId);
}
