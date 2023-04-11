package com.itWk.collect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itWk.Clients.ProductClient;
import com.itWk.POJO.Collect;
import com.itWk.Utils.Result;
import com.itWk.collect.mapper.CollectMapper;
import com.itWk.collect.service.CollectService;
import com.itWk.param.ProductCollectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private ProductClient productClient;
    /**
     * 收藏添加方法
     *
     * @param collect
     * @return
     */
    @Override
    public Result save(Collect collect) {
        //查询是否存在
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        //判断用户id和商品id是否存在
        queryWrapper.eq("user_id", collect.getUserId());
        queryWrapper.eq("product_id", collect.getProductId());
        Long count = collectMapper.selectCount(queryWrapper);
        if (count > 0){
            return Result.fail("收藏已经添加");
        }
        //不存在便添加
        collect.setCollectTime(System.currentTimeMillis());//添加收藏时间 获取当前系统时间
        int rows = collectMapper.insert(collect);
        log.info("CollectServiceImpl.save业务结束，结果:{}",rows);
        return Result.ok("收藏添加成功");
    }

    /**
     * 根据用户id查询商品收藏信息集合
     *
     * @param userId
     * @return
     */
    @Override
    public Result list(Integer userId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.select("product_id");
        //具体查询
        List<Object> idsobjects = collectMapper.selectObjs(queryWrapper);
        ProductCollectRequest productCollectRequest = new ProductCollectRequest();
        List<Integer> ids = new ArrayList<>();
        for (Object o : idsobjects){
            ids.add((Integer) o);
        }
        productCollectRequest.setProductIds(ids);

        Result result = productClient.productIds(productCollectRequest);
        log.info("CollectServiceImpl.list业务结束，结果:{}",result);
        return result;
    }

    /**
     * 根据用户id和商品id删除收藏数据
     *
     * @param collect@return
     */
    @Override
    public Result remove(Collect collect) {
        //封装删除参数
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", collect.getUserId());
        queryWrapper.eq("product_id", collect.getProductId());
        int rows = collectMapper.delete(queryWrapper);
        log.info("CollectServiceImpl.delete业务结束，结果:{}",rows);
        return Result.ok("删除成功");
    }

    /**
     * 根据商品id删除 （后台商品下架服务
     *
     * @param productId
     * @return
     */
    @Override
    public Result removeByPid(Integer productId) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        int rows = collectMapper.delete(queryWrapper);
        log.info("CollectServiceImpl.removeByPid业务结束，结果:{}",rows);
        return Result.ok("收藏商品删除成功");
    }
}
