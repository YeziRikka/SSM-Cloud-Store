package com.itWk.product.controller;

import com.alibaba.druid.sql.visitor.functions.Bin;
import com.itWk.Utils.Result;
import com.itWk.param.*;
import com.itWk.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品信息控制类
 */

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/promo")
    public Result promo(@RequestBody @Validated ProductPromoRequest productPromoRequest , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.fail("数据查询失败");
        }
        return productService.promo(productPromoRequest.getCategoryName());
    }

    @PostMapping("/hots")
    public Result hots(@RequestBody @Validated ProductHotRequest productHotRequest,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.fail("数据查询失败");
        }
        return productService.hots(productHotRequest);
    }

    @PostMapping("/category/list")
    public Result list(){
        return productService.list();
    }

    @PostMapping("/bycategory")
    public Result byCategory(@RequestBody @Validated ProductIdsRequest productIdsRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.fail("类别商品查询失败");
        }
        return productService.byCategory(productIdsRequest);
    }

    @PostMapping("/all")
    public Result all(@RequestBody @Validated ProductIdsRequest productIdsRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.fail("类别商品查询失败");
        }
        return productService.byCategory(productIdsRequest);
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody @Validated ProductIdRequest productIdRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.fail("商品详情查询失败");
        }
        return productService.detail(productIdRequest.getProductID());
    }

    @PostMapping("/pictures")
    public Result picture(@RequestBody @Validated ProductIdRequest productIdRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return Result.fail("商品图片详情查询失败");
        }
        return productService.picture(productIdRequest.getProductID());
    }

    @PostMapping("/search")
    public Result search(ProductSearchRequest productSearchRequest){
        return productService.search(productSearchRequest);
    }
}
