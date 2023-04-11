package com.itWk.Clients;

import com.itWk.POJO.Category;
import com.itWk.Utils.Result;
import com.itWk.param.PageRequest;
import com.itWk.param.ProductHotRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 类别服务客户端
 * 类别调用接口，由feign转成网络请求
 */

@FeignClient("category-service")
public interface CategoryClient {

    @GetMapping("/category/promo/{categoryName}")
    Result byName(@PathVariable String categoryName);

    @PostMapping("/category/hots")
    Result hots(@RequestBody ProductHotRequest productHotRequest);

    @GetMapping("category/list")
    Result list();

    @PostMapping("/category/admin/list")
    Result adminPageList(@RequestBody PageRequest pageRequest);

    @PostMapping("/category/admin/save")
    Result adminSave(@RequestBody Category category);

    @PostMapping("/category/admin/remove")
    Result adminRemove(@RequestBody Integer categoryId);

    @PostMapping("/category/admin/update")
    Result adminUpdate(@RequestBody Category category);
}
