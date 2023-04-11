package com.itWk.product.controller;

import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.ProductSaveRequest;
import com.itWk.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台商品服务调用控制类
 */
@RestController
@RequestMapping("product")
public class ProductAdminController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/count")
    public Long adminCount(@RequestBody Integer categoryId){
        return productService.adminCount(categoryId);
    }

    @PostMapping("/admin/save")
    public Result adminSave(@RequestBody ProductSaveRequest productSaveRequest){
        return productService.adminSave(productSaveRequest);
    }

    @PostMapping("/admin/update")
    public Result adminUpdate(@RequestBody Product product){
        return productService.adminUpdate(product);
    }

    @PostMapping("/admin/remove")
    public Result adminRemove(@RequestBody Integer productId){
        return productService.adminRemove(productId);
    }
}
