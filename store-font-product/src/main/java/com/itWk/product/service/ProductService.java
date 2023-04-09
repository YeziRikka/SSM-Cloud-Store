package com.itWk.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.ProductHotRequest;
import com.itWk.param.ProductIdRequest;
import com.itWk.param.ProductIdsRequest;
import com.itWk.param.ProductSearchRequest;
import com.itWk.to.OrderToProduct;

import java.util.List;

public interface ProductService extends IService<Product>{

    /**
     * 根据单类别名称，查询热门商品
     * @param categoryName
     * @return
     */
    Result promo(String categoryName);

    /**
     * 多类别热门商品查询，根据类别名称查询
     * @param
     * @return
     */
    Result hots(ProductHotRequest productHotRequest);

    /**
     * 查询全部类别商品
     * @return
     */
    Result lists();

    /**
     * 通用业务
     * 传入类别id根据id查询并分页
     * 没有传入则查询全部信息
     * @param productIdsRequest
     * @return
     */
    Result byCategory(ProductIdsRequest productIdsRequest);

    /**
     * 商品详情 根据商品id查询商品详情
     * @param
     * @return
     */
    Result detail(Integer productID);

    /**
     * 根据id查询商品图片详情
     * @param productID
     * @return
     */
    Result picture(Integer productID);

    /**
     * 获取全部商品数据，同步搜索服务
     * @return
     */
    List<Product> allList();

    /**
     * 搜索业务
     * 调用搜索服务
     * @param productSearchRequest
     * @return
     */
    Result search(ProductSearchRequest productSearchRequest);

    /**
     * 根据商品id集合查询商品信息（收藏服务）
     * @param productIds
     * @return
     */
    Result ids(List<Integer> productIds);

    /**
     * 根据商品id 查询id集合
     * @param productIds
     * @return
     */
    List<Product> cartList(List<Integer> productIds);

    /**
     * 修改库存
     * 增加销售量
     * @param orderToProductList
     */
    void subNumber(List<OrderToProduct> orderToProductList);
}
