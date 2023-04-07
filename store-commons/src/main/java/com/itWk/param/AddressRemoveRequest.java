package com.itWk.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 接受地址移除参数
 */
@Data
public class AddressRemoveRequest {
    @NotNull
    private Integer id;

}
