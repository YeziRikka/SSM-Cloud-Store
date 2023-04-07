package com.itWk.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 购物车查询接受参数
 */
@Data
public class CartListRequest {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;


}
