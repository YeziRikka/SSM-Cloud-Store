package com.itWk.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itWk.Clients.*;
import com.itWk.POJO.Category;
import com.itWk.POJO.Picture;
import com.itWk.POJO.Product;
import com.itWk.Utils.Result;
import com.itWk.param.*;
import com.itWk.product.mapper.ProductMapper;
import com.itWk.product.mapper.ProductPictureMapper;
import com.itWk.product.service.ProductService;
import com.itWk.to.OrderToProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类别实现类
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements ProductService {

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
    private OrderClient orderClient;
    @Autowired
    private CartClient cartClient;
    @Autowired
    private CollectClient collectClient;
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
    public Result lists() {
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

    /**
     * 根据商品id 查询id集合
     *
     * @param productIds
     * @return
     */
    @Override
    public List<Product> cartList(List<Integer> productIds) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", productIds);
        List<Product> productList = productMapper.selectList(queryWrapper);
        log.info("ProductServiceImpl.cartList业务结束，结果:{}",productList);
        return productList;
    }

    /**
     * 修改库存
     * 增加销售量
     *
     * @param orderToProductList
     */
    @Override
    public void subNumber(List<OrderToProduct> orderToProductList) {
        //转为MAP
        Map<Integer, OrderToProduct> orderToProductMap = orderToProductList.stream()
                .collect(Collectors.toMap(OrderToProduct::getProductId, v -> v));
        //获取商品id的集合
        Set<Integer> productIds = orderToProductMap.keySet();
        //查询商品id集合对应的商品信息，得到商品集合
        //使用批量查询
        List<Product> productList = productMapper.selectBatchIds(productIds);
        //修改商品信息 遍历
        for (Product product : productList){
            Integer num = orderToProductMap.get(product.getProductId()).getNum();
            //库存减少
            product.setProductNum(product.getProductNum() - num);
            //销售量增加
            product.setProductSales(product.getProductSales() + num);
        }
        //批量更新
        this.updateBatchById(productList);
        log.info("ProductServiceImpl.cartList业务结束，结果:库存和销售量修改完毕");
    }

    /**
     * 类别对应商品数量查询
     *
     * @param categoryId
     * @return
     */
    @Override
    public Long adminCount(Integer categoryId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        Long count = baseMapper.selectCount(queryWrapper);
        log.info("ProductServiceImpl.adminCount业务结束，结果:",count);
        return count;
    }

    /**
     * 后端商品保存业务
     *
     * @param productSaveRequest
     * @return
     */
    @CacheEvict(value = "list.product",allEntries = true)
    @Override
    public Result adminSave(ProductSaveRequest productSaveRequest) {
        /**
         * 商品数据保存
         * 商品图片详情切割与保存
         * ES搜索数据库的数据添加
         * 清空商品相关的  缓存数据
         */
        //商品数据保存
        Product product = new Product();
        BeanUtils.copyProperties(productSaveRequest, product);//将productSaveRequest 中的数据 保存到product中，获得对象
        int rows = productMapper.insert(product);//商品数据插入
        log.info("ProductServiceImpl.adminSave业务结束，结果:商品保存成功",rows);
        //商品图片获取
        String pictures = productSaveRequest.getPictures();
        if (!StringUtils.isEmpty(pictures)){
            //截取特殊字符串需要用到 \\
            String[] urls = pictures.split("\\+");
//            List<Picture> pictureList = new ArrayList<>();
            for (String url : urls){
                Picture picture = new Picture();
                picture.setProductId(product.getProductId());
                picture.setProductPicture(url);
                productPictureMapper.insert(picture);//插入商品图片
            }
        }
        //同步ES搜索服务的数据
        searchClient.saveOrUpdate(product);
        return Result.ok("商品数据保存成功");
    }

    /**
     * 后端商品更新业务
     *
     * @param product
     * @return
     */
    @Override
    public Result adminUpdate(Product product) {
        /**
         * 更新商品数据
         * 同步搜索服务
         */
        productMapper.updateById(product);
        //同步ES搜索服务的数据
        searchClient.saveOrUpdate(product);
        return Result.ok("商品数据更新成功");
    }

    /**
     * 商品删除业务
     *
     * @param productId
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(value = "product.list",allEntries = true),
                    @CacheEvict(value = "product", key = "#productId")

            }
    )
    @Override
    public Result adminRemove(Integer productId) {
        /**
         * 检查购物车
         * 检查订单
         * 删除商品
         * 删除商品图片
         * 删除收藏
         * 进行ES搜索数据同步
         * 清空缓存
         */
        //检查购物车
        Result result = cartClient.check(productId);
        if ("004".equals(result.getCode())){
            log.info("ProductServiceImpl.adminRemove业务结束，结果:",result.getMsg());
            return result;
        }
        //检查订单
        result = orderClient.check(productId);
        if ("004".equals(result.getCode())){
            log.info("ProductServiceImpl.adminRemove业务结束，结果:",result.getMsg());
            return result;
        }
        //删除商品
        productMapper.deleteById(productId);
        //删除商品图片
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        productPictureMapper.delete(queryWrapper);
        //删除收藏中与本商品相关的内容
        collectClient.remove(productId);
        //同步ES搜索服务中的数据
        searchClient.remove(productId);
        return Result.ok("商品删除成功");

    }

}
