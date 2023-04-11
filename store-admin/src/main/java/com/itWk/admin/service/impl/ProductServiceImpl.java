package com.itWk.admin.service.impl;

import com.itWk.Clients.ProductClient;
import com.itWk.Clients.SearchClient;
import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.admin.service.ProductService;
import com.itWk.param.ProductSaveRequest;
import com.itWk.param.ProductSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SearchClient searchClient;
    @Autowired
    private ProductClient productClient;
    /**
     * 全部商品查询和搜索方法
     * @param productSearchRequest
     * @return
     */
    @Override
    public Result search(ProductSearchRequest productSearchRequest) {
        Result search = searchClient.search(productSearchRequest);
        log.info("ProductServiceImpl.search业务结束，结果:{}",search);
        return search;
    }

    /**
     * 商品添加方法
     *
     * @param productSaveRequest
     * @return
     */
    @Override
    public Result save(ProductSaveRequest productSaveRequest) {
        Result result = productClient.adminSave(productSaveRequest);
        log.info("ProductServiceImpl.save业务结束，结果:{}",result);
        return result;
    }

    /**
     * 商品更新
     *
     * @param product
     * @return
     */
    @Override
    public Result update(Product product) {
        Result result = productClient.adminUpdate(product);
        log.info("ProductServiceImpl.update业务结束，结果:{}",result);
        return result;
    }

    /**
     * 后台商品删除服务
     *
     * @param productId
     * @return
     */
    @Override
    public Result remove(Integer productId) {
        Result result = productClient.adminRemove(productId);
        log.info("ProductServiceImpl.adminRemove业务结束，结果:{}",result);
        return result;
    }
}
