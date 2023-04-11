package com.itWk.collect.service;

import com.itWk.POJO.Collect;
import com.itWk.Utils.Result;

public interface CollectService {
    /**
     * 收藏添加方法
     * @param collect
     * @return
     */
    Result save(Collect collect);

    /**
     * 根据用户id查询商品收藏信息集合
     * @param userId
     * @return
     */
    Result list(Integer userId);

    /**
     * 根据用户id和商品id删除收藏数据
     * @param collect
     * @return
     */
    Result remove(Collect collect);

    /**
     * 根据商品id删除 （后台商品下架服务
     * @param productId
     * @return
     */
    Result removeByPid(Integer productId);
}
