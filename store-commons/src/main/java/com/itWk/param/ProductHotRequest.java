package com.itWk.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 热门商品接受参数
 */
@Data
public class ProductHotRequest {

    @NotEmpty //集合使用该注解判断
    private List<String> categoryName;
}
