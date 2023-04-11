package com.itWk.search.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.doc.ProductDoc;
import com.itWk.param.ProductSearchRequest;
import com.itWk.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
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
        for (SearchHit hitsHit : hitsHits){
            //查询的内容数据,ProductDoc模型对应的json数据
            String sourceAsString = hitsHit.getSourceAsString();
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
        Result result = Result.ok(null, productList, total);
        log.info("ProductServiceImpl.search业务结束，结果:{}",result);
        return result;
    }

    /**
     * 同步调用，进行商品插入，覆盖更新
     * 商品同步，插入和更新
     *
     * @param product
     * @return
     */
    @Override
    public Result save(Product product) throws IOException {
        IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
        ProductDoc productDoc = new ProductDoc(product);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDoc);
        indexRequest.source(json, XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return Result.ok("数据同步成功");
    }

    /**
     * 进行ES库的商品删除
     *
     * @param productId
     * @return
     */
    @Override
    public Result remove(Integer productId) throws IOException {
        DeleteRequest request = new DeleteRequest("product").id(productId.toString());
        restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        return Result.ok("ES库商品信息删除成功");
    }
}
