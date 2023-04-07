package com.itWk.product.controller;

import com.itWk.Utils.Result;
import com.itWk.param.ProductCollectRequest;
import com.itWk.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品收藏控制类
 */

@RestController
@RequestMapping("product")
public class ProductCollectController {

    @Autowired
    private ProductService productService;
    @PostMapping("/collect/list")
    public Result productIds(@RequestBody @Validated ProductCollectRequest productCollectRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.ok("没有收藏商品");
        }
        return productService.ids(productCollectRequest.getProductIds());
    }
}
