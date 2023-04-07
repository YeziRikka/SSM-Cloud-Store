package com.itWk.param;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 地址结合参数接受
 */
@Data
public class AddressListRequest {

        @NotNull
        @JsonProperty("user_id")
        private Integer userId;
}
