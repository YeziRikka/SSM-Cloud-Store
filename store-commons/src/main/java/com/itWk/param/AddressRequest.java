package com.itWk.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itWk.POJO.Address;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 地址接收值参数类
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressRequest {

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    private Address add;
}
