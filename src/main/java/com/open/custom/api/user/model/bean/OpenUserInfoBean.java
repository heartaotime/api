package com.open.custom.api.user.model.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OpenUserInfoBean {

    private String userCode;

    private String appCode;

    private String userName;

    private String email;

    private String nickName;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date birthday;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createDate;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date updateDate;

    private String birthday;

    private String createDate;

    private String updateDate;


    private String remark;

    private String verifyCode;



    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}