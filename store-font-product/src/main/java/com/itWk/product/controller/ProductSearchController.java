package com.itWk.product.controller;

import com.itWk.POJO.Product;
import com.itWk.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品搜索服务控制类
 */
@RestController
@RequestMapping("/product")
public class ProductSearchController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> allList(){
        return productService.allList();
    }
}
