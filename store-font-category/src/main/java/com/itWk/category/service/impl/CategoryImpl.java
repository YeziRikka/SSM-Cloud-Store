package com.itWk.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itWk.Clients.ProductClient;
import com.itWk.POJO.Category;
import com.itWk.Utils.Result;
import com.itWk.category.mapper.CategoryMapper;
import com.itWk.category.service.CategoryService;
import com.itWk.param.PageRequest;
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
    @Autowired
    private ProductClient productClient;

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

    /**
     * 后台管理分页查询
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Result listPage(PageRequest pageRequest) {
        IPage<Category> page = new Page<>(pageRequest.getCurrentPage(),pageRequest.getPageSize());
        page = categoryMapper.selectPage(page, null);
        return Result.ok("类别分页查询成功",page.getRecords(),page.getTotal());
    }

    /**
     * 添加类别信息
     *
     * @param category
     * @return
     */
    @Override
    public Result adminSave(Category category) {
        //判断类别名称是否存在
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name", category.getCategoryName());
        Long count = categoryMapper.selectCount(queryWrapper);
        if (count > 0){
            return Result.fail("该商品类别已经存在");
        }
        int insert = categoryMapper.insert(category);
        log.info("CategoryServiceImpl.adminSave业务结束，结果:",insert);
        return Result.ok("商品类别添加成功");
    }

    /**
     * 删除数据
     *
     * @param categoryId
     * @return
     */
    @Override
    public Result adminRemove(Integer categoryId) {
        Long aLong = productClient.adminCount(categoryId);
        if (aLong > 0){
            return Result.fail("类别删除成功，当前类别中有:" + aLong + "件商品，无法删除");
        }
        int i = categoryMapper.deleteById(categoryId);
        log.info("CategoryServiceImpl.adminRemove业务结束，结果:",i);
        return Result.ok("类别商品数据删除成功");
    }

    /**
     * 类别修改
     *
     * @param category
     * @return
     */
    @Override
    public Result adminUpdate(Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name", category.getCategoryName());
        Long count = categoryMapper.selectCount(queryWrapper);
        if (count > 0){
            return Result.fail("商品类别已经存在，修改失败");
        }
        int i = categoryMapper.updateById(category);
        log.info("CategoryServiceImpl.adminUpdate业务结束，结果:",i);
        return Result.ok("商品类别数据修改成功");
    }
}
