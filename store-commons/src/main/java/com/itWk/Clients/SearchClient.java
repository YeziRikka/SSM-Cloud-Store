package com.itWk.Clients;

import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.ProductSearchRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 搜索服务客户端
 */

@FeignClient("search-service")
public interface SearchClient {

    @PostMapping("/search/product")
    Result search(@RequestBody ProductSearchRequest productSearchRequest);

    @PostMapping("/search/save")
    Result saveOrUpdate(@RequestBody Product product);

    @PostMapping("/search/remove")
    Result remove(@RequestBody Integer productId);
}
