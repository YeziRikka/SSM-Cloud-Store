package com.itWk.param;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录参数
 */

@Data
public class UserLoginRequest {

    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
