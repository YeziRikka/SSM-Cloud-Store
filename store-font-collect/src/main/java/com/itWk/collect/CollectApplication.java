package com.itWk.collect;

import com.itWk.Clients.CategoryClient;
import com.itWk.Clients.ProductClient;
import com.itWk.Clients.SearchClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 收藏服务启动类
 */

@SpringBootApplication
@MapperScan(basePackages = "com.itWk.collect.mapper")
@EnableFeignClients(clients = {ProductClient.class})
public class CollectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectApplication.class, args);
    }
}
