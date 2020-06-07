package com.open.custom.api.model.extend;

import com.open.custom.api.model.OpenUserInfo;

public class OpenUserInfoExtend extends OpenUserInfo {

    private String birthdayStr;

    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }
}