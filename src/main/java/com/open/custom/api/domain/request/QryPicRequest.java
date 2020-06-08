package com.open.custom.api.domain.request;

import com.open.custom.api.domain.common.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import javax.smartcardio.CommandAPDU;

public class QryPicRequest extends CommonRequest {

//    @ApiModelProperty(value = "查询类型 1：必应 2：")
//    private String qryType;

    @ApiModelProperty(value = "图片类型  1：PC 2：手机")
    private String picType;

    @ApiModelProperty(value = "分类 1：随机（默认） 2：风景 3：动漫")
    private String classify;

    @ApiModelProperty(value = "图片来源 1：随机（默认） 2：必应")
    private String picOrigin;


    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getPicOrigin() {
        return picOrigin;
    }

    public void setPicOrigin(String picOrigin) {
        this.picOrigin = picOrigin;
    }
}
