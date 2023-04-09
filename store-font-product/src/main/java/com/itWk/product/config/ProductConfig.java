package com.itWk.product.config;

import com.itWk.config.CaCheConfig;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 商品模块配置类
 */
@Configuration
public class ProductConfig extends CaCheConfig {
    /**
     * 序列化方式
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){

        return new Jackson2JsonMessageConverter();
    }
}
