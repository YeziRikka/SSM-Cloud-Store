package com.itWk.category.controller;

import com.itWk.POJO.Category;
import com.itWk.Utils.Result;
import com.itWk.category.service.CategoryService;
import com.itWk.param.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理分类处理控制类
 */

@RestController
@RequestMapping("/category")
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/admin/list")
    public Result listPage(@RequestBody PageRequest pageRequest){
        return categoryService.listPage(pageRequest);
    }

    @PostMapping("/admin/save")
    public Result adminSave(@RequestBody Category category){
        return categoryService.adminSave(category);
    }

    @PostMapping("/admin/remove")
    public Result adminRemove(@RequestBody Integer categoryId){
        return categoryService.adminRemove(categoryId);
    }

    @PostMapping("/admin/update")
    public Result adminUpdate(@RequestBody Category category){
        return categoryService.adminUpdate(category);
    }
}
