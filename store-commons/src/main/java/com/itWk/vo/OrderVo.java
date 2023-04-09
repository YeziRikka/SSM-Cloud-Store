package com.itWk.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itWk.POJO.Order;
import lombok.Data;
/**
 * 订单返回数据实体
 * 查询订单需要返回结果
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderVo extends Order {

    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_picture")
    private String productPicture;

}