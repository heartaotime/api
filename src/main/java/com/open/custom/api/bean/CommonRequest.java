package com.open.custom.api.bean;


/**
 * Create with IntelliJ IDEA.
 * Description:
 * Author: huxintao
 * Date: 2019-11-15
 */
public class CommonRequest<T> {

    private String appCode;

    private T param;

    private Boolean pageFlag = true;
    private Integer pageNum = 1;
    private Integer pageSize = 5;

    private Boolean readRedis = true;

    private Boolean recursion = false; // 是否需要递归获取子类


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Boolean getRecursion() {
        return recursion;
    }

    public void setRecursion(Boolean recursion) {
        this.recursion = recursion;
    }

    public Boolean getReadRedis() {
        return readRedis;
    }

    public void setReadRedis(Boolean readRedis) {
        this.readRedis = readRedis;
    }

    //    Map<String, String> param = new HashMap<>();


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


    public Boolean getPageFlag() {
        return pageFlag;
    }

    public void setPageFlag(Boolean pageFlag) {
        this.pageFlag = pageFlag;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
