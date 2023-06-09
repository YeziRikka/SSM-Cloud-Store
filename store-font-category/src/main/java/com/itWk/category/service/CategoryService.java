package com.itWk.category.service;


import com.itWk.POJO.Category;
import com.itWk.Utils.Result;
import com.itWk.param.PageRequest;
import com.itWk.param.ProductHotRequest;

public interface CategoryService {
    /**
     * 根据类别名称，查询类别参数
     * @param categoryName
     * @return
     */
    Result byName(String categoryName);

    /**
     * 根据传入的热门类别名称集合，返回类别对应的id集合
     * @param productHotRequest
     * @return
     */
    Result hotsCategory(ProductHotRequest productHotRequest);

    /**
     * 查询并返回类别数据
     * @return
     */
    Result list();

    /**
     * 后台管理分页查询
     * @param pageRequest
     * @return
     */
    Result listPage(PageRequest pageRequest);

    /**
     * 添加类别信息
     * @param category
     * @return
     */
    Result adminSave(Category category);

    /**
     * 删除数据
     * @param categoryId
     * @return
     */
    Result adminRemove(Integer categoryId);

    /**
     * 类别修改
     * @param category
     * @return
     */
    Result adminUpdate(Category category);
}
