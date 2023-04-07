package com.itWk.category.controller;


import com.itWk.Utils.Result;
import com.itWk.category.service.CategoryService;
import com.itWk.param.ProductHotRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 类别控制器类
 */

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据名称查询对应的类别
     * @return
     */
    @GetMapping("/promo/{categoryName}")
    public Result byName(@PathVariable String categoryName){
        //判断字符串是否为空
        if (StringUtils.isEmpty(categoryName)){
            return Result.fail("类别参数为空，查询失败");
        }
        return categoryService.byName(categoryName);
    }

    /**
     * 按照id查询热门类别
     * @param productHotRequest
     * @param bindingResult
     * @return
     */
    @PostMapping("/hots")
    public Result hotsCategory(@RequestBody @Validated ProductHotRequest productHotRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return Result.fail("类别集合查询失败");
        }
        return categoryService.hotsCategory(productHotRequest);
    }

    @GetMapping("/list")
    public Result list(){
        return categoryService.list();
    }
}
