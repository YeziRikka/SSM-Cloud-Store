package com.itWk.POJO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("carousel")
public class Carousel  implements Serializable {

    public static final Long serialVersionUID = 1L;

    @TableId(value = "carousel_id",type = IdType.AUTO)
    @JsonProperty("carousel_id")
    private Integer carouselId;

    @TableField("img_path")
    private String  imgPath;

    private String  describes;

    @JsonProperty("product_id")
    @TableField("product_id")
    private Integer productId;

    private Integer priority;//轮播图优先级


}