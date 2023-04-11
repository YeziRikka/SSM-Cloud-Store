package com.itWk.admin.controller;

import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.admin.service.ProductService;
import com.itWk.admin.utils.AliyunOSSUtils;
import com.itWk.param.ProductSaveRequest;
import com.itWk.param.ProductSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 商品后台控制类
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @GetMapping("/list")
    public Result adminList(ProductSearchRequest productSearchRequest){
        return productService.search(productSearchRequest);
    }

    @PostMapping("/upload")
    public Result adminUpload(@RequestParam("img") MultipartFile img) throws Exception {
        String filename = img.getOriginalFilename();//获取名称
        //避免重复 加上uid
        filename = UUID.randomUUID().toString().replaceAll("-", "") + filename;
        String contentType = img.getContentType();//获取类型
        byte[] imgBytes = img.getBytes();//获取内容
        int hours = 1000;//定义存储时间
        String url = aliyunOSSUtils.uploadImage(filename, imgBytes, contentType, hours);
        System.out.println("url:"+url);
        return Result.ok("图片上传成功",url);
    }

    @PostMapping("/save")
    public Result adminSave(ProductSaveRequest productSaveRequest){
        return productService.save(productSaveRequest);
    }

    @PostMapping("/update")
    public Result adminUpdate(Product product){
        return productService.update(product);
    }

    @PostMapping("/remove")
    public Result adminRemove(Integer productId){
        return productService.remove(productId);
    }
}
