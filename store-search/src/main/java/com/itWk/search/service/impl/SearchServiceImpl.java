package com.itWk.search.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.ProductSearchRequest;
import com.itWk.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    /**
     * 根据关键字和分页进行数据库查询操作
     *
     * @param productSearchRequest
     * @return
     */
    @Override
    public Result search(ProductSearchRequest productSearchRequest) {
        SearchRequest searchRequest = new SearchRequest("product");
        String search = productSearchRequest.getSearch();
        if (StringUtils.isEmpty(search)){
            //为空，不添加all关键字，查询全部
            searchRequest.source().query(QueryBuilders.matchAllQuery());//查询全部数据
        }else{
            //不为空,添加all匹配
            /**
             * name：字段 后面的属性：要匹配的字段
             */
            searchRequest.source().query(QueryBuilders.matchQuery("all", search));
        }
        //分页数据添加
        searchRequest.source().from(
                (productSearchRequest.getCurrentPage() - 1) * productSearchRequest.getPageSize()
        );//起始查询数据，偏移量 (当前页数-1)×页面容量
        //查询大小
        searchRequest.source().size(productSearchRequest.getPageSize());
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("查询错误");
        }
        //获取结果数据对象
        SearchHits hits = searchResponse.getHits();
        //查询符合的数量
        long total = hits.getTotalHits().value;
        //hits 里面的hits就是所要的数据了
        //拿到数据集合
        SearchHit[] hitsHits = hits.getHits();
        List<Product> productList = new ArrayList<>();
        //json处理器
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hitHit : hitsHits){
            //查询的内容数据,ProductDoc模型对应的json数据
            String sourceAsString = hitHit.getSourceAsString();
            //转为product对象
            Product product = null;
            try {
                //productDoc里面对一个ALL属性，转换会出异常，利用jackson注解忽略该属性
                //TODO：添加忽略没有属性的注解
                product = objectMapper.readValue(sourceAsString, Product.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            productList.add(product);
        }
        //封装结果
        Result ok = Result.ok(null, productList, total);
        log.info("ProductServiceImpl.search业务结束，结果:{}",ok);
        return ok;
    }
}
