package com.itWk.param;

import lombok.Data;

/**
 * 分页参数复用类
 */
@Data
public class PageRequest {

    private int currentPage = 1;
    private int pageSize = 15;
}
