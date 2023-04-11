package com.itWk.admin.service.impl;

import com.itWk.Clients.CategoryClient;
import com.itWk.POJO.Category;
import com.itWk.Utils.Result;
import com.itWk.admin.service.CategoryService;
import com.itWk.param.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 类别业务实现类
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryClient categoryClient;
    /**
     * 商品类别分页查询
     *
     * @param pageRequest
     * @return
     */
    @Cacheable(value = "list.category",key =
            "#pageRequest.currentPage + '-' + #pageRequest.pageSize")
    @Override
    public Result pageList(PageRequest pageRequest) {
        Result result = categoryClient.adminPageList(pageRequest);
        log.info("CategoryServiceImpl.pageList业务结束，结果:{}",result);
        return result;
    }

    /**
     * 商品类别新增
     *
     * @param category
     * @return
     */
    @CacheEvict(value = "list.category",allEntries = true)
    @Override
    public Result save(Category category) {
        Result result = categoryClient.adminSave(category);
        log.info("CategoryServiceImpl.save业务结束，结果:{}",result);
        return result;
    }

    /**
     * 根据id删除类别数据
     *
     * @param categoryId
     * @return
     */
    @CacheEvict(value = "list.category",allEntries = true)
    @Override
    public Result remove(Integer categoryId) {
        Result result = categoryClient.adminRemove(categoryId);
        log.info("CategoryServiceImpl.remove业务结束，结果:{}",result);
        return result;
    }

    /**
     * 修改类别信息
     *
     * @param category
     * @return
     */
    @CacheEvict(value = "list.category",allEntries = true)
    @Override
    public Result update(Category category) {
        Result result = categoryClient.adminUpdate(category);
        log.info("CategoryServiceImpl.update业务结束，结果:{}",result);
        return result;
    }
}
