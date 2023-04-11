package com.itWk.admin.service;


import com.itWk.POJO.Category;
import com.itWk.Utils.Result;
import com.itWk.param.PageRequest;

public interface CategoryService {
    /**
     * 商品类别分页查询
     * @param pageRequest
     * @return
     */
    Result pageList(PageRequest pageRequest);

    /**
     * 商品类别新增
     * @param category
     * @return
     */
    Result save(Category category);

    /**
     * 根据id删除类别数据
     * @param categoryId
     * @return
     */
    Result remove(Integer categoryId);

    /**
     * 修改类别信息
     * @param category
     * @return
     */
    Result update(Category category);
}
