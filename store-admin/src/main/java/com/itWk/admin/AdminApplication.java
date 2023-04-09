package com.itWk.admin;

import com.itWk.Clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 后端服务启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "com.itWk.admin.mapper")
@EnableCaching // 缓存
@EnableFeignClients(clients = {UserClient.class})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
