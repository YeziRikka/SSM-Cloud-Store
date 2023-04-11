package com.itWk.search.controller;

import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.ProductSearchRequest;

import com.itWk.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 搜索服务控制类
 */

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/product")
    public Result searchProduct(@RequestBody ProductSearchRequest productSearchRequest){
        return searchService.search(productSearchRequest);
    }

    @PostMapping("/save")
    public Result saveProduct(@RequestBody Product product) throws IOException {
        return searchService.save(product);
    }

    @PostMapping("/remove")
    public Result removeProduct(@RequestBody Integer productId) throws IOException {
        return searchService.remove(productId);
    }
}
