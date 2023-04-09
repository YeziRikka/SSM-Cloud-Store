package com.itWk.admin.controller;

import com.itWk.POJO.User;
import com.itWk.Utils.Result;
import com.itWk.admin.service.UserService;
import com.itWk.param.CartListRequest;
import com.itWk.param.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户模块调用控制类
 *
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result userList(PageRequest pageRequest){
        return userService.userList(pageRequest);
    }

    @PostMapping("/remove")
    public Result userRemove(CartListRequest cartListRequest){
        return userService.userRemove(cartListRequest);
    }

    @PostMapping("/update")
    public Result userUpdate(User user){
        return userService.userUpdate(user);
    }

    @PostMapping("/save")
    public Result userSave(User user){
        return userService.userSave(user);
    }
}
