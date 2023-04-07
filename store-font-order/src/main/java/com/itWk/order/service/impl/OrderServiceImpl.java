package com.itWk.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itWk.POJO.Order;
import com.itWk.Utils.Result;
import com.itWk.order.mapper.OrderMapper;
import com.itWk.order.service.OrderService;
import com.itWk.param.OrderRequest;
import com.itWk.to.OrderToProduct;
import com.itWk.vo.CartVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis继承ServiceImpl接口，提供多种批量操作的方法，而mapper没有封装这些方法
 */

public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    /**
     * 订单生成方法
     *
     * @param orderRequest
     * @return
     */
    @Transactional //开启事务
    @Override
    public Result save(OrderRequest orderRequest) {
        /**
         * 准备数据
         */
        List<Integer> cartIds = new ArrayList<>(); //修改清空购物车的参数
        List<OrderToProduct> orderToProducts = new ArrayList<>(); //商品修改库存参数集合
        List<Order> orderList = new ArrayList<>(); //修改批量插入数据库的参数
        /**
         * 生成数据
         */
        Integer userId = orderRequest.getUserId();
        long orderId = System.currentTimeMillis(); //用时间戳生成商品订单
        for (CartVo cartVo : orderRequest.getProducts()){
            cartIds.add(cartVo.getId()); //保存删除的购物车项id
            OrderToProduct orderToProduct = new OrderToProduct();
            orderToProduct.setNum(cartVo.getNum());
            orderToProduct.setProductId(cartVo.getProductID());
            //商品服务修改的数据
            orderToProducts.add(orderToProduct);

            //订单信息保存
            Order order = new Order();
            order.setOrderId(orderId);
            order.setUserId(userId);
            order.setOrderTime(orderId);
            order.setProductId(cartVo.getProductID());
            order.setProductNum(cartVo.getNum());
            order.setProductPrice(cartVo.getPrice());
            orderList.add(order); //添加用户信息
        }
        /**
         * 订单数据批量保存
         * 父类提供的批量增加操作
         */
        saveBatch(orderList);

        return null;
    }
}
