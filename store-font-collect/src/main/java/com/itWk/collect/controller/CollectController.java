package com.itWk.collect.controller;

import com.itWk.POJO.Collect;
import com.itWk.Utils.Result;
import com.itWk.collect.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收藏服务控制类
 */

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping("/save")
    public Result save(@RequestBody Collect collect){
        return collectService.save(collect);
    }

    @PostMapping("/list")
    public Result list(@RequestBody Collect collect){
        return collectService.  list(collect.getUserId());
    }

    @PostMapping("/remove")
    public Result remove(@RequestBody Collect collect){
        return collectService.remove(collect);
    }

    @PostMapping("/remove/product")
    public Result removeByPid(@RequestBody Integer productId){
        return collectService.removeByPid(productId);
    }
}
