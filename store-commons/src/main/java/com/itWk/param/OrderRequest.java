package com.itWk.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itWk.vo.CartVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 订单接受参数类
 */
@Data
public class OrderRequest implements Serializable {

    public static final Long serialVersionUID = 1L;

    @JsonProperty("user_id")
    private Integer userId;
    private List<CartVo> products;

}
