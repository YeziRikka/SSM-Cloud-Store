package com.itWk.search.service;

import com.itWk.Utils.Result;
import com.itWk.param.ProductSearchRequest;

public interface SearchService {

    /**
     * 根据关键字和分页进行数据库查询操作
     * @param productSearchRequest
     * @return
     */
    Result search(ProductSearchRequest productSearchRequest) ;
}
