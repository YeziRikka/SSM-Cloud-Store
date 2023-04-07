package com.itWk.param;

import lombok.Data;

/**
 * 搜索关键字和分页参数集合
 */
@Data
public class ProductSearchRequest extends PageRequest{
    private String search;
}
