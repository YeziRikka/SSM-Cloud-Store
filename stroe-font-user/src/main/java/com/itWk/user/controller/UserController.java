package com.itWk.user.controller;


import com.itWk.POJO.User;
import com.itWk.Utils.Result;
import com.itWk.param.UserCheckRequest;
import com.itWk.param.UserLoginRequest;
import com.itWk.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userservice;

    /**
     * TODO：检查账号是否可用的接口
     * @Validated 注解用来使校验注解生效
     * bindingResult 获取校验是否成功还是失败
     * @param userCheckRequest 接受检查的账号实体
     * @return 返回Result对象
     */
    @PostMapping("/check")
    public Result UserCheckRequest(@RequestBody @Validated UserCheckRequest userCheckRequest, BindingResult bindingResult){
        //该方法用于检查是否符合校验注解的规则 符合返回 false
        if (bindingResult.hasErrors()){
            //如果存在异常，证明请求参数不符合注解要求
            return Result.fail("该账号不存在！");
        }

        return userservice.check(userCheckRequest);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return Result.fail("请输入正确信息");
        }
        return userservice.register(user);
    }

    @PostMapping("/login")
    public Result login(@RequestBody @Validated UserLoginRequest userLoginRequest,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return Result.fail("登录失败");
        }
        return userservice.login(userLoginRequest);
    }

}
