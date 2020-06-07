package com.open.custom.api.app.model;

import java.util.Date;

public class OpenApiAccess {
    private Integer id;

    private String apiMethod;

    private String apiMethodClass;

    private Date accessDate;

    private Integer duraTime;

    private String clientIp;

    private String httpMethod;

    private String appCode;

    private String userCode;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod == null ? null : apiMethod.trim();
    }

    public String getApiMethodClass() {
        return apiMethodClass;
    }

    public void setApiMethodClass(String apiMethodClass) {
        this.apiMethodClass = apiMethodClass == null ? null : apiMethodClass.trim();
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public Integer getDuraTime() {
        return duraTime;
    }

    public void setDuraTime(Integer duraTime) {
        this.duraTime = duraTime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod == null ? null : httpMethod.trim();
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}