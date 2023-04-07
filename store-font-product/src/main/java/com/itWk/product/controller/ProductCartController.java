package com.itWk.product.controller;

import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.ProductCollectRequest;
import com.itWk.param.ProductIdRequest;
import com.itWk.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车调用商品服务的控制类
 */

@RequestMapping("product")
@RestController
public class ProductCartController {
    @Autowired
    private ProductService productService;

    @PostMapping("cart/detail")
    public Product cdetail(@RequestBody @Validated ProductIdRequest productIdRequest, BindingResult bindingResult){
        if ((bindingResult.hasErrors())){
            return  null;
        }
        Result detail = productService.detail(productIdRequest.getProductID());
        Product product = (Product) detail.getData(); //强转类型
        return product;
    }

    /**
     *
     * @param productCollectRequest 只有一个ids集合参数，可以在这里进行复用
     * @return
     */
    @PostMapping("/cart/list")
    public List<Product> cartList(@RequestBody @Validated ProductCollectRequest productCollectRequest,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ArrayList<Product>();//返回一个空的数组，防止报空指针异常
        }
        return productService.cartList(productCollectRequest.getProductIds());
    }


}
