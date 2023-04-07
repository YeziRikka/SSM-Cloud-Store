package com.itWk.param;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 使用jsr 303的注解进行参数校验
 */
@Data
public class UserCheckRequest {

    @NotBlank //字符串不允许为空和空字符串
    private String userName;

}
