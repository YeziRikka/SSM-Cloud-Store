package com.itWk.Clients;

import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.ProductCollectRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 商品服务客户端
 */

@FeignClient(value = "product-service")
public interface ProductClient {
    /**
     * 搜索服务进行调用查询全部数据后与ES搜索服务数据库同步数据
     * @return
     */
    @GetMapping("/product/list")
    List<Product> allList();

    /**
     * 收藏服务
     * @param productCollectRequest
     * @return
     */
    @PostMapping("/product/collect/list")
    Result productIds(@RequestBody ProductCollectRequest productCollectRequest);
}
