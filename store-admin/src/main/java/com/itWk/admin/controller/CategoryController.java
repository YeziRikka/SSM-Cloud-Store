package com.itWk.admin.controller;

import com.itWk.POJO.Category;
import com.itWk.Utils.Result;
import com.itWk.admin.service.CategoryService;
import com.itWk.param.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类别调用控制类
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result pageList(PageRequest pageRequest){
        return categoryService.pageList(pageRequest);
    }

    @PostMapping("/save")
    public Result adminSave(Category category){
        return categoryService.save(category);
    }

    @PostMapping("/remove")
    public Result adminRemove(Integer categoryId){
        return categoryService.remove(categoryId);
    }

    @PostMapping("/update")
    public Result adminRemove(Category category){
        return categoryService.update(category);
    }

}
