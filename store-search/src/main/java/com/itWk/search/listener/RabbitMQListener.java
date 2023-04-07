package com.itWk.search.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itWk.POJO.Product;
import com.itWk.doc.ProductDoc;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消息通知监听类
 */

@Component
public class RabbitMQListener {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 监听并插入数据
     * @param product
     */
    @RabbitListener( //绑定监听队列
            bindings = {
                    @QueueBinding(
                            value = @Queue(name = "inset.queue"),//绑定消息队列
                            exchange = @Exchange("topic.ex"), //绑定交换机
                            key = "product.insert"
                    )
            }
    )
    public void insert(Product product) throws IOException {
        IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
        ProductDoc productDoc = new ProductDoc(product);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDoc);
        indexRequest.source(json, XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "remove.queue"),
                    exchange = @Exchange("topic.ex"),
                    key = "product.remove"
            )
    )
    public void remove(Integer productId) throws IOException {
        DeleteRequest request = new DeleteRequest("product").id(productId.toString());
        restHighLevelClient.delete(request, RequestOptions.DEFAULT);
    }

}
