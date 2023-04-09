package com.itWk.cart.listener;

import com.itWk.cart.service.CartService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MQ消息监听类
 */

@Component
public class CartRabbitMqListener {

    @Autowired
    private CartService cartService;

    /**
     * 清空购物车数据
     */

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "clear.queue"),
                    exchange = @Exchange(value = "topic.ex"),
                    key = "clear.cart"
            )
    )
    public void Clear(List<Integer> cardIds){
        cartService.clearIds(cardIds);
    }
}
