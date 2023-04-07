package com.itWk.POJO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 实现序列化接口序列化
 */

//自动生成get set方法
@Data
//Mybatis plus指明使用那个表
@TableName("user")
public class User implements Serializable {

    public static final Long seriaVersionUID = 1L;

    @JsonProperty("user_id") //进行属性格式化，与数据库字段名匹配
    @TableId(type = IdType.AUTO) //指明主键并且设置为自增长
    private Integer userId;

    @Length(min = 6) //指定最小长度
    private  String userName;

    //@JsonIgnore //忽略该属性不生成不接受json
    @JsonInclude(JsonInclude.Include.NON_NULL)//值不为空的时候生成json 不影响接受json
    @NotBlank
    @Length(min = 6,max = 12    )
    private  String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private  String userPhonenumber;

}
