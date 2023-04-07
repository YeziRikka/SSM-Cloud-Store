package com.itWk.doc;

import com.itWk.POJO.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用来存储商品搜索数据的实体类
 */
@Data
@NoArgsConstructor
public class ProductDoc extends Product {

    /**
     * 商品名称，商品标题，商品描述
     */
    private String all;

    public ProductDoc(Product product) {
        //借用父类中的构造方法，需要到父类中创建有参数的构造方法
        super(product.getProductId(),product.getProductName(),
                product.getCategoryId(),product.getProductTitle(),
                product.getProductIntro(),product.getProductPicture(),
                product.getProductPrice(),product.getProductSellingPrice(),
                product.getProductNum(),product.getProductSales());
        this.all = product.getProductName()+product.getProductTitle()+product.getProductIntro();
    }
}
