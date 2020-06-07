package com.open.custom.api.model.bean;

public class OpenUserInfoExtBean {

    private String userCode;

    private String userSet;

    private String userSet1;

    private String userSet2;


    private String createDate;

    private String updateDate;

    private String remark;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public String getUserSet() {
        return userSet;
    }

    public void setUserSet(String userSet) {
        this.userSet = userSet == null ? null : userSet.trim();
    }

    public String getUserSet1() {
        return userSet1;
    }

    public void setUserSet1(String userSet1) {
        this.userSet1 = userSet1 == null ? null : userSet1.trim();
    }

    public String getUserSet2() {
        return userSet2;
    }

    public void setUserSet2(String userSet2) {
        this.userSet2 = userSet2 == null ? null : userSet2.trim();
    }
}