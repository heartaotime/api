package com.open.custom.api.response;

/**
 * Created by lcl on 2019/2/17.
 */
public class CommonReturnType {

    //表明对应请求的返回处理结果success 或fail
    private  String status;

    //若status=success，则data 返回json
    //若status=fail,则data返回通用的错误码格式
    private  Object data;

    //定义一个通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result,String status){
       CommonReturnType type=new CommonReturnType();
       type.setStatus(status);
       type.setData(result);
       return type;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
