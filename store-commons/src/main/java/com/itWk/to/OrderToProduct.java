package com.itWk.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单发送商品服务实体类
 */
@Data
public class OrderToProduct implements Serializable {

    public static final Long serialVersionUID = 1L;

    @JsonProperty("product_id")
    private Integer productId;
    private Integer num;

}
