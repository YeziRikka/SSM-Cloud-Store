package com.itWk.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itWk.Clients.ProductClient;
import com.itWk.POJO.Cart;
import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.cart.mapper.CartMapper;
import com.itWk.cart.service.CartService;
import com.itWk.param.CartRequest;
import com.itWk.param.CartSaveRequest;
import com.itWk.param.ProductCollectRequest;
import com.itWk.param.ProductIdRequest;
import com.itWk.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductClient productClient;
    @Autowired
    private CartMapper cartMapper;

    /**
     * 购物车添加功能
     *
     * @param cartSaveRequest
     * @return
     */
    @Override
    public Result save(CartSaveRequest cartSaveRequest) {
        //查询商品数据
        ProductIdRequest productIdRequest = new ProductIdRequest();
        productIdRequest.setProductID(cartSaveRequest.getProductId());
        Product product = productClient.productDetail(productIdRequest);
        if (product == null){
            return Result.fail("商品信息为空，添加失败");
        }
        //检查库存
        if (product.getProductNum() == 0){
            Result ok = Result.ok("商品缺货，无法购买");
            ok.setCode("003");
            log.info("CartServiceImpl.save业务结束",ok);
            return ok;
        }
        //检查是否添加过该商品，如果添加过则数量+1
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cartSaveRequest.getUserId());
        queryWrapper.eq("product_id", cartSaveRequest.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);
        if (cart != null){
            //商品存在 数量+1
            cart.setNum(cart.getNum() + 1);
            cartMapper.updateById(cart);
            Result ok = Result.ok("购物车存在商品，数量+1");
            ok.setCode("002");
            log.info("CartServiceImpl.save业务结束",ok);
            return ok;
        }
        //添加购物车
        cart = new Cart();
        cart.setNum(1); //不存在 为购物车添加数量1
        cart.setUserId(cartSaveRequest.getUserId());
        cart.setProductId(cartSaveRequest.getProductId());
        int rows = cartMapper.insert(cart);
        log.info("CartServiceImpl.save业务结束",rows);
        //封装数据
        CartVo cartVo = new CartVo(product,cart);
        return Result.ok("购物车添加成功",cartVo);
    }

    /**
     * 查询购物车数据 返回集合
     *
     * @param userId
     * @return
     */
    @Override
    public Result list(Integer userId) {
        //根据用户id查询购物车数据
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_Id", userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        //判断数据是否存在
        if (cartList == null || cartList.size() == 0){
            cartList = new ArrayList<>(); //存在，则返回一个新的空集合
            return Result.ok("购物车为空",cartList);
        }
        //存在则调用商品id集合，调用商品服务进行查询
        List<Integer> productIds = new ArrayList<>();
        for (Cart cart : cartList){
            productIds.add(cart.getProductId());
        }
        ProductCollectRequest productCollectRequest = new ProductCollectRequest();
        productCollectRequest.setProductIds(productIds);
        List<Product> productList = productClient.cartList(productCollectRequest);
        /**
         * 将商品集合转为MAP集合
         * 商品id == key 商品信息 == value
         * jdk 8 提供的stream流里面的收集方法进行转换
         */
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        //vo封装，有几个购物车封装几个VO
        List<CartVo> cartVoList = new ArrayList<>();
        for (Cart cart : cartList){
            CartVo cartVo = new CartVo(productMap.get(cart.getProductId()),cart);
            cartVoList.add(cartVo);
        }
        Result ok = Result.ok("购物车数据查询成功", cartVoList);
        log.info("CartServiceImpl.list业务结束",ok);
        return ok;
    }

    /**
     * 更新购物车
     *
     * @param cart
     * @return
     */
    @Override
    public Result update(Cart cart) {
        //查询商品数据
        ProductIdRequest productIdRequest = new ProductIdRequest();
        productIdRequest.setProductID(cart.getProductId());
        Product product = productClient.productDetail(productIdRequest);
        //判断库存是否可用
        if (cart.getNum() > product.getProductNum()){
            log.info("CartServiceImpl.update业务结束","修改失败");
            return Result.fail("库存不足，修改失败");
        }
        //修改
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("product_id", cart.getProductId());
//        Cart newCart = cartMapper.selectOne(queryWrapper);
//        newCart.setNum(cart.getNum());
        Cart cart1 = cartMapper.selectOne(queryWrapper);
        cart1.setNum(cart.getNum());
        int rows = cartMapper.updateById(cart1);
        log.info("CartServiceImpl.update业务结束",rows);
        return Result.ok("购物车数量修改成功");
    }

    /**
     * 删除
     *
     * @param cart
     * @return
     */
    @Override
    public Result remove(Cart cart) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("product_id", cart.getProductId());
        int rows = cartMapper.delete(queryWrapper);
        log.info("CartServiceImpl.remove业务结束",rows);
        return Result.ok("购物车删除成功");
    }

    /**
     * 清空购物车
     *
     * @param cardIds
     */
    @Override
    public void clearIds(List<Integer> cardIds) {
//      批量删除
        cartMapper.deleteBatchIds(cardIds);
        log.info("CartServiceImpl.clearIds业务结束",cardIds);
    }
}
