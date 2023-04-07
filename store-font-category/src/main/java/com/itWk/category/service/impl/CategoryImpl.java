package com.itWk.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itWk.POJO.Category;
import com.itWk.Utils.Result;
import com.itWk.category.mapper.CategoryMapper;
import com.itWk.category.service.CategoryService;
import com.itWk.param.ProductHotRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据类别名称，查询类别参数
     *
     * @param categoryName
     * @return
     */
    @Override
    public Result byName(String categoryName) {
        //封装查询参数
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        //比较
        categoryQueryWrapper.eq("category_name", categoryName);
        //查询数据库
        Category category = categoryMapper.selectOne(categoryQueryWrapper);
        //结果封装
        if (category == null){
            log.info("CategoryServiceImpl.byName业务结束，结果:类别查询失败");
            return Result.fail("类别查询失败");
        }
        log.info("CategoryServiceImpl.byName业务结束，结果:类别查询成功");
        return Result.ok("类别查询成功",category);
    }

    /**
     * 根据传入的热门类别名称集合，返回类别对应的id集合
     *
     * @param productHotRequest
     * @return
     */
    @Override
    public Result hotsCategory(ProductHotRequest productHotRequest) {
        //封装查询参数
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        //集合使用该方法进行比较
        queryWrapper.in("category_name", productHotRequest.getCategoryName());
        //指定查询列
        queryWrapper.select("category_id");

        //查询数据库
        List<Object> ids = categoryMapper.selectObjs(queryWrapper);
        Result ok = Result.ok("类别集合查询成功", ids);
        log.info("CategoryServiceImpl.hotsCategory业务结束，结果:类别集合 查询成功",ok);
        return ok;
    }

    /**
     * 查询并返回类别数据
     *
     * @return
     */
    @Override
    public Result list() {
        //查询全部
        List<Category> categoryList = categoryMapper.selectList(null);
        Result ok = Result.ok("全部数据查询成功", categoryList);
        log.info("CategoryServiceImpl.list业务结束，结果:类别集合 查询成功",ok);
        return ok;
    }
}
