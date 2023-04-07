package com.itWk.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 接受收藏商品参数
 */

@Data
public class ProductCollectRequest {

    @NotEmpty
    private List<Integer> productIds;
}
