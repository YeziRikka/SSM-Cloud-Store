package com.itWk.search.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itWk.Clients.ProductClient;
import com.itWk.POJO.Product;
import com.itWk.doc.ProductDoc;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@NoArgsConstructor
public class SpringBootListener implements ApplicationRunner {

    /**
     * 注入配置类中的ES工具对象
     */
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 注入客户端
     */
    @Autowired
    private ProductClient productClient;

    /**
     * 创建索引
     */
    private String indexStr = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"productId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productName\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"categoryId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productTitle\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productIntro\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productPicture\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"productPrice\":{\n" +
            "        \"type\": \"double\",\n" +
            "        \"index\": true\n" +
            "      },\n" +
            "      \"productSellingPrice\":{\n" +
            "        \"type\": \"double\"\n" +
            "      },\n" +
            "      \"productNum\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productSales\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";


    /**
     * 完成ES数据同步操作
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //判断es中是否存在索引
//      GetIndexRequest getIndexRequest = new GetIndexRequest("product");
        //不要导错包了！找了好久的bug
        GetIndexRequest getIndexRequest = new GetIndexRequest("product");
        //存在为true
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (exists){
            //如果存在的话，就删除全部数据
            DeleteByQueryRequest queryRequest = new DeleteByQueryRequest("product");
            //全部删除 分布式搜索Elasticsearch——QueryBuilders.matchAllQuery
            //TODO：QueryBuilder中不存在该方法
            queryRequest.setQuery(QueryBuilders.matchAllQuery());
            restHighLevelClient.deleteByQuery(queryRequest, RequestOptions.DEFAULT);
        }else{
            //不存在 创建索引
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("product");
            createIndexRequest.source(indexStr, XContentType.JSON);
            restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        }
        //查询全部商品数据
        List<Product> productList = productClient.allList();;
        //执行批量插入操作
        BulkRequest bulkRequest = new BulkRequest();

        /**
         * JSON转换对象
         */
        ObjectMapper objectMapper = new ObjectMapper();
        for (Product product:productList){
            ProductDoc productDoc = new ProductDoc(product);
            /**
             * 插入数据
             */
            IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
            //将ProductDoc转换成JSON并放入
            String json = objectMapper.writeValueAsString(productDoc);
            indexRequest.source(json,XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }
}
