package com.itWk.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itWk.Clients.CategoryClient;
import com.itWk.Clients.SearchClient;
import com.itWk.POJO.Category;
import com.itWk.POJO.Picture;
import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.ProductHotRequest;
import com.itWk.param.ProductIdRequest;
import com.itWk.param.ProductIdsRequest;
import com.itWk.param.ProductSearchRequest;
import com.itWk.product.mapper.ProductMapper;
import com.itWk.product.mapper.ProductPictureMapper;
import com.itWk.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 类别实现类
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    /**
     * 通过这个调用对应的category服务
     */
    @Autowired
    private CategoryClient categoryClient;
    /**
     * 注入搜索服务客户端
     */
    @Autowired
    private SearchClient searchClient;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductPictureMapper productPictureMapper;
    /**
     * 根据单类别名称，查询热门商品
     *
     * @param categoryName
     * @return
     */

    @Cacheable(value = "list.product",key = "#categoryName" ,cacheManager = "cacheManagerHour")
    //该注解开启缓存，配置缓存时间
    @Override
    public Result promo(String categoryName) {
        Result result = categoryClient.byName(categoryName);
        //获取code值进行对比 判断是否成功
        if (result.getCode().equals(Result.FAIL_CODE)){
            log.info("ProductServiceImpl.promo业务结束，结果:{}","类别查询失败!");
            return Result.fail("类别查询失败");
        }
//        Category category = (Category) result.getData();
//        Integer categoryId = category.getCategoryId();
        LinkedHashMap<String,Object> map = (LinkedHashMap<String, Object>) result.getData();

        Integer categoryId = (Integer) map.get("category_id");
        //封装结构
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        //根据销量排序
        queryWrapper.orderByDesc("product_sales");

        /**
         * 分页查询
         */
        IPage<Product> page = new Page<>(1,7);
        //page中包括对应的商品集合
        page = productMapper.selectPage(page, queryWrapper);
        //查询指定页数据
        List<Product> productList = page.getRecords();
        //获取总条数
        long total = page.getTotal();
        log.info("ProductServiceImpl.promo业务结束，结果:{}",productList);
        return Result.ok("数据查询成功",productList);
    }

    /**
     * 多类别热门商品查询，根据类别名称查询
     *
     * @param productHotRequest@return
     */
    @Cacheable(value = "list.product",key = "#productHotRequest.categoryName")
    @Override
    public Result hots(ProductHotRequest productHotRequest) {
        Result result = categoryClient.hots(productHotRequest);
        if(result.getCode().equals(Result.FAIL_CODE)){
            log.info("ProductServiceImpl.hots业务结束，结果:{}",result.getMsg());
            return result;
        }
        List<Object> ids = (List<Object>) result.getData();
        //商品数据查询
        //封装查询条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_id", ids);
        queryWrapper.orderByDesc("product_sales");
        //封装查询分页参数
        IPage<Product> page = new Page<>(1,7);
        page = productMapper.selectPage(page, queryWrapper);
        List<Product> hotsList = page.getRecords();
        //封装处理返回结果
        Result ok = Result.ok("多类别热门商品查询成功", hotsList);
        log.info("ProductServiceImpl.hots业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 查询全部类别商品
     *
     * @return
     */
    @Override
    public Result list() {
        Result list = categoryClient.list();
        log.info("ProductServiceImpl.list业务结束，结果:{}",list);
        return list;
    }

    /**
     * 通用业务
     * 传入类别id根据id查询并分页
     * 没有传入则查询全部信息
     *
     * @param productIdsRequest
     * @return
     */
    @Cacheable(value = "list.product",key = "#productIdsRequest.categoryID+'-'+#productIdsRequest.currentPage+'-'+#productIdsRequest.currentPage")
    @Override
    public Result byCategory(ProductIdsRequest productIdsRequest) {
        List<Integer> categoryId = productIdsRequest.getCategoryID();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if (!categoryId.isEmpty()){
            queryWrapper.in("category_id", categoryId);
        }
        IPage<Product> page = new Page<>(productIdsRequest.getCurrentPage(),productIdsRequest.getPageSize());
        page = productMapper.selectPage(page, queryWrapper);
        Result ok = Result.ok("查询成功", page.getRecords(), page.getTotal());
        log.info("ProductServiceImpl.byCategory业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 商品详情 根据商品id查询商品详情
     *
     * @param productID@return
     */

    @Cacheable(value = "product",key = "#productID")
    @Override
    public Result detail(Integer productID) {
        Product product = productMapper.selectById(productID);
        Result ok = Result.ok(product);
        log.info("ProductServiceImpl.detail业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 根据id查询商品图片详情
     *
     * @param productID
     * @return
     */
    @Cacheable(value = "picture",key = "#productID")
    @Override
    public Result picture(Integer productID) {
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productID);
        List<Picture> pictureList = productPictureMapper.selectList(queryWrapper);
        Result ok = Result.ok(pictureList);
        log.info("ProductServiceImpl.picture业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 获取全部商品数据，同步搜索服务
     * @return
     */
    @Cacheable(value = "list.category",key = "#root.methodName",cacheManager ="cacheManagerDay")
    @Override
    public List<Product> allList() {
        List<Product> list = productMapper.selectList(null);
        log.info("ProductServiceImpl.alllist业务结束，结果:{}",list.size());
        return list;
    }

    /**
     * 搜索业务
     * 调用搜索服务
     *
     * @param productSearchRequest
     * @return
     */
    @Override
    public Result search(ProductSearchRequest productSearchRequest) {
        Result result = searchClient.search(productSearchRequest);
        log.info("ProductServiceImpl.search业务结束，结果:{}",result);
        return result;
    }

    /**
     * 根据商品id集合查询商品信息（收藏服务）
     *
     * @param productIds
     * @return
     */
    @Cacheable(value = "list.product",key = "#productIds")
    @Override
    public Result ids(List<Integer> productIds) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", productIds);
        List<Product> productList = productMapper.selectList(queryWrapper);
        Result ok = Result.ok("类别信息查询成功", productList);
        log.info("ProductServiceImpl.ids业务结束，结果:{}",ok);
        return ok;
    }

}
