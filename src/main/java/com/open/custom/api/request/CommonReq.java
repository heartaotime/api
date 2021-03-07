package com.open.custom.api.request;


import java.io.Serializable;

public class CommonReq<T> implements Serializable {

    private T data;

    private boolean isPage = true; // 是否分页

    private Integer pageNum = 1; // 当前页码

    private Integer pageSize = 10; // 每页条数

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isPage() {
        return isPage;
    }

    public void setPage(boolean page) {
        isPage = page;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

