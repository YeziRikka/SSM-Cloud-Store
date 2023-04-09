package com.itWk.admin.controller;

import com.itWk.Utils.Result;
import com.itWk.admin.param.AdminUserRequest;
import com.itWk.admin.pojo.AdminUser;
import com.itWk.admin.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 后台管理用户控制类
 */
@RestController
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/user/login")
    public Result login(@Validated AdminUserRequest adminUserRequest, BindingResult bindingResult, HttpSession httpSession){
        if (bindingResult.hasErrors()){
            return Result.fail("参数异常，登录失败");
        }
        /**
         * 验证码存在session中
         * 所以这里在形参中加入 httpservice, 获取到session
         * key = captcha
         * TODO:验证码校验
         */
        String captcha = (String) httpSession.getAttribute("captcha");
        if (! adminUserRequest.getVerCode().equalsIgnoreCase(captcha)){
            return Result.fail("验证码错误！");
        }
        AdminUser user = adminUserService.login(adminUserRequest);
        if (user == null){
             return Result.fail("账号或密码错误");
        }
        httpSession.setAttribute("userInfo", user);
        return Result.ok("登录成功");
    }

    @GetMapping("/user/logout")
    public Result logout(HttpSession httpSession){
        /**
         * 退出登录后清空session数据
         * 为的是做登录保护
         * 原理：判断session中是否存有数据，没有数据则没有登录，不让其登录到后台管理
         */
        httpSession.invalidate();//该方法清空session中所有数据
        return Result.ok("退出登录成功");
    }
}
