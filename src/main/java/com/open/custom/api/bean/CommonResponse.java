package com.open.custom.api.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * Author: huxintao
 * Date: 2019-11-15
 */
public class CommonResponse<T> {

    @ApiModelProperty(value = "消息码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String message;

    @ApiModelProperty(value = "数据")
    private T data;

    public CommonResponse() {
        this.code = "0";
        this.message = "success";
    }

    public CommonResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
