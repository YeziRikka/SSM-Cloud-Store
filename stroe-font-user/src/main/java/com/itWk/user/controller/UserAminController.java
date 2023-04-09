package com.itWk.user.controller;

import com.itWk.POJO.User;
import com.itWk.Utils.Result;
import com.itWk.param.CartListRequest;
import com.itWk.param.PageRequest;
import com.itWk.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理的控制类
 */
@RestController
@RequestMapping("/user")
public class UserAminController {

    @Autowired
    private UserService userService;

    @PostMapping("/admin/list")
    public Result listPage(@RequestBody PageRequest pageRequest){
        return userService.listPage(pageRequest);
    }

    @PostMapping("/admin/remove")
    public Result remove(@RequestBody CartListRequest cartListRequest){
        return userService.remove(cartListRequest.getUserId());
    }

    @PostMapping("/admin/update")
    public Result update(@RequestBody User user){
        return userService.update(user);
    }

    @PostMapping("/admin/save")
    public Result save(@RequestBody User user){
        return userService.save(user);
    }
}
