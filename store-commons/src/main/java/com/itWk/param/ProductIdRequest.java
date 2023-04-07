package com.itWk.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 商品详情 接受商品id参数
 */
@Data
public class ProductIdRequest {

    @NotNull
    private Integer productID;
}
