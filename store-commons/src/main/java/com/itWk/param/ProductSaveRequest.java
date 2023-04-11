package com.itWk.param;

import com.itWk.POJO.Product;
import lombok.Data;

/**
 * 后台商品数据添加接受类
 */
@Data
public class ProductSaveRequest extends Product {

    /**
     * 保存商品详情的图片地址，图片之间使用+连接
     */
    private String pictures;
}
