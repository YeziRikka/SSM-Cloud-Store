package com.itWk.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.ProductSearchRequest;

import java.io.IOException;

public interface SearchService {

    /**
     * 根据关键字和分页进行数据库查询操作
     * @param productSearchRequest
     * @return
     */
    Result search(ProductSearchRequest productSearchRequest) ;

    /**
     * 同步调用，进行商品插入，覆盖更新
     * 商品同步，插入和更新
     * @param product
     * @return
     */
    Result save(Product product) throws IOException;

    /**
     * 进行ES库的商品删除
     * @param productId
     * @return
     */
    Result remove(Integer productId) throws IOException;
}
