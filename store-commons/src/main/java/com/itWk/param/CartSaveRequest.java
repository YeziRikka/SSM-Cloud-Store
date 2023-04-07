package com.itWk.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 购物车添加参数接受
 */
@Data
public class CartSaveRequest {

    @NotNull
    @JsonProperty("product_id")
    private Integer productId;

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;
}
