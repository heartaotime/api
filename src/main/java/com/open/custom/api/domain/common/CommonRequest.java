package com.open.custom.api.domain.common;


import io.swagger.annotations.ApiModelProperty;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * Author: huxintao
 * Date: 2019-11-15
 */
public class CommonRequest<T> {

    @ApiModelProperty(value = "应用编码")
    private String appCode;

    @ApiModelProperty(value = "参数")
    private T param;

    @ApiModelProperty(value = "是否分页")
    private Boolean pageFlag = true;

    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页条数", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "是否读取redis")
    private Boolean readRedis = true;


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
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
