package com.itWk.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 类别商品展示
 */
@Data
public class ProductIdsRequest extends PageRequest{

    @NotNull
    private List<Integer> categoryID;


}
