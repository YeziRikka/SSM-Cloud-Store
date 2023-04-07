package com.itWk.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    public static final Long serialVersionUID = 1L;

    /**
     * 通用成功状态码
     */
    public static final String SUCCESS_CODE = "001";
    /**
     * 失败状态码
     */
    public static final String FAIL_CODE = "004";
    /**
     * 未登录
     */
    public static final String USER_NO_LOGIN = "401";


    private String code; //状态码
    @JsonInclude(JsonInclude.Include.NON_NULL) //生成JSON的时候如果没有赋值，则会忽略此参数的生成
    private String msg;  //信息
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data; //封装数据
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total; //总数


    /**
     * 成功
     * @param msg
     * @param data
     * @return
     */
    public static Result ok(String msg, Object data, Long total){

        return new Result(SUCCESS_CODE,msg,data,total);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static Result ok(String msg, Object data){

        return ok(msg,data,null);
    }

    /**
     * 成功
     * @return
     */
    public static Result ok(String msg){

        return ok(msg,null);
    }


    /**
     * 成功
     * @return
     */
    public static Result ok(Object data){

        return ok(null,data);
    }



    /**
     * 失败
     * @param msg
     * @param data
     * @return
     */
    public static Result fail(String msg, Object data, Long total){

        return new Result(FAIL_CODE,msg,data,total);
    }

    /**
     * 失败
     * @param data
     * @return
     */
    public static Result fail(String msg, Object data){

        return fail(msg,data,null);
    }

    /**
     * 失败
     * @return
     */
    public static Result fail(String msg){

        return fail(msg,null);
    }


    /**
     * 失败
     * @return
     */
    public static Result fail(Object data){

        return fail(null,data);
    }


    /**
     * 未登录
     * @return
     */
    public static Result NO_LOGIN(){

        return fail(USER_NO_LOGIN,"用户未登录!");
    }

}
