package com.itWk.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itWk.Clients.ProductClient;
import com.itWk.POJO.Order;
import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.order.mapper.OrderMapper;
import com.itWk.order.service.OrderService;
import com.itWk.param.OrderRequest;
import com.itWk.param.PageRequest;
import com.itWk.param.ProductCollectRequest;
import com.itWk.to.OrderToProduct;
import com.itWk.vo.AdminOrderVo;
import com.itWk.vo.CartVo;
import com.itWk.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * mybatis继承ServiceImpl接口，提供多种批量操作的方法，而mapper没有封装这些方法
 */

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private OrderMapper orderMapper;

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
        //发送购物车消息
        rabbitTemplate.convertAndSend("topic.ex","clear.cart",cartIds);
        //发送商品服务消息
        rabbitTemplate.convertAndSend("topic.ex","sub.number",orderToProducts);
        return Result.ok( "订单保存成功");
    }

    /**
     * 分组查询订单数据
     *
     * @param userId
     * @return
     */
    @Override
    public Result lists(Integer userId) {
        //查询用户对应的全部订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Order> list = list(queryWrapper);
        //利用steam流进行订单分组
        Map<Long, List<Order>> listMap = list.stream().collect(Collectors.groupingBy(Order::getOrderId));
        //查询订单的全部商品集合，并转为map
        List<Integer> productIds = list.stream().map(Order::getProductId).collect(Collectors.toList());
        ProductCollectRequest productCollectRequest = new ProductCollectRequest();
        productCollectRequest.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectRequest);//获得商品集合
        //转为map
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        //封装返回的VO对象
        //结果封装
        List<List<OrderVo>> result = new ArrayList<>();

        for (List<Order> orders : listMap.values()) {
            List<OrderVo> orderVos = new ArrayList<>();
            for (Order order : orders) {
                //返回vo数据封装
                OrderVo orderVo = new OrderVo();
                Product product = productMap.get(order.getProductId());
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());
                orderVo.setId(order.getId());
                orderVo.setOrderId(order.getOrderId());
                orderVo.setOrderTime(order.getOrderTime());
                orderVo.setProductNum(order.getProductNum());
                orderVo.setProductId(order.getProductId());
                orderVo.setProductPrice(order.getProductPrice());
                orderVo.setUserId(order.getUserId());
                orderVos.add(orderVo);
            }
            result.add(orderVos);
        }
        Result resultOk = Result.ok("订单数据获取成功", result);
        log.info("OrderServiceImpl.lists业务结束",resultOk);
        return resultOk;
    }

    /**
     * 检查订单中是否有商品引用
     *
     * @param productId
     * @return
     */
    @Override
    public Result check(Integer productId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        Long count = baseMapper.selectCount(queryWrapper);
        if (count > 0){
            return Result.fail("订单中有：" + count + "项数据引用，无法删除");
        }
        return Result.ok("订单中无引用，可以删除");
    }

    /**
     * 后台管理查询全部订单数据方法
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Result adminList(PageRequest pageRequest) {
        //计算分页参数
        int offset = (pageRequest.getCurrentPage() - 1 ) * pageRequest.getPageSize();
        int pageSize = pageRequest.getPageSize();
        List<AdminOrderVo> adminOrderVoList = orderMapper.selectAdminOrder(offset,pageSize);
        return Result.ok("订单数据查询成功",adminOrderVoList);
    }
}
