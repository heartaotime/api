package com.open.custom.api.response;


import com.open.custom.api.constant.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonRsp<T> implements Serializable {

    private String rspCode;
    private String rspType;
    private String rspMsg;


    private T data;

    private Long total;

    private Integer pages;

    //实体类集合
    private List<T> rows = new ArrayList<T>();


    public CommonRsp() {
        // 默认失败
        this.rspCode = Constant.RSP_CODE.FAIL;
        this.rspType = Constant.RSP_TYPE.FAIL;
        this.rspMsg = Constant.RSP_MSG.FAIL;
    }

    public CommonRsp(boolean isSuccess) {
        if (isSuccess) {
            this.rspCode = Constant.RSP_CODE.SUCCESS;
            this.rspType = Constant.RSP_TYPE.SUCCESS;
            this.rspMsg = Constant.RSP_MSG.SUCCESS;
        } else {
            this.rspCode = Constant.RSP_CODE.FAIL;
            this.rspType = Constant.RSP_TYPE.FAIL;
            this.rspMsg = Constant.RSP_MSG.FAIL;
        }
    }


    public CommonRsp failed(String rspMsg) {
        this.rspCode = Constant.RSP_CODE.FAIL;
        this.rspType = Constant.RSP_TYPE.FAIL;
        this.rspMsg = rspMsg;
        return this;
    }

    public CommonRsp failed(String rspMsg, T data) {
        this.rspCode = Constant.RSP_CODE.FAIL;
        this.rspType = Constant.RSP_TYPE.FAIL;
        this.rspMsg = rspMsg;
        this.data = data;
        return this;
    }

    public CommonRsp(String rspCode, String rspType, String rspMsg) {
        this.rspCode = rspCode;
        this.rspType = rspType;
        this.rspMsg = rspMsg;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspType() {
        return rspType;
    }

    public void setRspType(String rspType) {
        this.rspType = rspType;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
