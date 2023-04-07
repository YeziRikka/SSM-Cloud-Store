package com.itWk.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 类别热门商品参数接受
 */

@Data
public class ProductPromoRequest {

    @NotBlank
    private String categoryName;
}
