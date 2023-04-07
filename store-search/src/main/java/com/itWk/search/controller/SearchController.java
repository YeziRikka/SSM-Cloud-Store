package com.itWk.search.controller;

import com.itWk.Utils.Result;
import com.itWk.param.ProductSearchRequest;

import com.itWk.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
