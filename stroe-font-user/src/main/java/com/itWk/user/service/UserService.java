package com.itWk.user.service;

import com.itWk.POJO.User;
import com.itWk.Utils.Result;
import com.itWk.param.UserCheckRequest;
import com.itWk.param.UserLoginRequest;

public interface UserService {
    /**
     * TODO：检查账号是否可用业务
     * @param userCheckRequest 账号参数已校验完毕
     * @return 返回检查结果编码
     */
    Result check(UserCheckRequest userCheckRequest);

    /**
     * 注册业务
     * @param user
     * @return
     */
    Result register(User user);


    /**
     * 登录业务
     * @param userLoginRequest
     * @return
     */
    Result login(UserLoginRequest userLoginRequest);

}
