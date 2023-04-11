package com.itWk.admin.service;

import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.ProductSaveRequest;
import com.itWk.param.ProductSearchRequest;

public interface ProductService {
    /**
     * 全部商品查询和搜索方法
     * @param productSearchRequest
     * @return
     */
    Result search(ProductSearchRequest productSearchRequest);

    /**
     * 商品添加方法
     * @param productSaveRequest
     * @return
     */
    Result save(ProductSaveRequest productSaveRequest);

    /**
     * 商品更新
     * @param product
     * @return
     */
    Result update(Product product);

    /**
     * 后台商品删除服务
     * @param productId
     * @return
     */
    Result remove(Integer productId);
}
