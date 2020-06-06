# 开放接口服务

## 使用须知

+ 接口免费提供服务，请合理使用
+ 为了防止接口滥用，调用接口需要提供 `appCode` ，所以使用接口前先邮件 `heartatime@foxmail.com` ，注册一个应用
+ 邮件中主要需要说明的模板如下（没有的填写暂无）：
  + 1.应用名称：
  + 2.应用描述：
  + 3.地址：
  + 4.你的邮箱：
  + 5.其他：

### 接口调用相关
> 目前暂时提供一个测试专用 `appCode=b198c18f49354a51984581cc124d985b` 
#### 每个接口的请求协议都是POST
接口的请求格式如下，其中 `appCode` 为每个应用的编码并且必传，`param` 为请求参数的消息体
```
{
    "appCode": "xxx",
    "param": {
        "xxx": "xx"
    }
}
```
接口调用示例：
```
### 
POST https://www.myindex.top/api/user/v1/checkUserExist
Content-Type: application/json;charset=UTF-8

{
    "appCode": "b198c18f49354a51984581cc124d985b",
    "param": {
        "userName": "heartaotime"
    }
}

```
返回成功示例：
```
{
    "code": "0",
    "message": "success",
    "data": null
}
```
返回失败示例：
```
{
    "code": "-1",
    "message": "该用户已存在[heartaotime]",
    "data": null
}
```

### 提供的接口如下 （参数标记 * 为必传）

+ 校验用户名或邮箱是否已注册
```
### 校验用户是否存在
POST https://www.myindex.top/api/user/v1/checkUserExist
Content-Type: application/json;charset=UTF-8

{
    "appCode": "",
    "param": {
        "userName": ""
    }
}

* userName:用户名/邮箱
```

+ 发送验证码邮件
```
### 发送验证码邮件
POST https://www.myindex.top/api/user/v1/sendEMail
Content-Type: application/json;charset=UTF-8

{
    "appCode": "",
    "param": {
        "email": ""
    }
}

* email:邮箱
```

+ 注册用户
```
### 注册用户
POST https://www.myindex.top/api/user/v1/registUser
Content-Type: application/json;charset=UTF-8

{
    "appCode": "",
    "param": {
        "userName": "",
        "passWord": "",
        "email": "",
        "verifyCode": "",
        "birthday": "",
        "nickName": "",
        "remark": ""
    }
}

* userName:用户名
* passWord:密码
* email:邮箱
* verifyCode:验证码
  birthday:生日（格式 yyyy-MM-dd 如 2020-12-12）
  nickName:昵称
  remark:备注
```

+ 用户登陆
```
### 用户登陆
POST https://www.myindex.top/api/user/v1/loginUser
Content-Type: application/json;charset=UTF-8

{
    "appCode": "",
    "param": {
        "userName": "",
        "passWord": ""
    }
}

* userName:用户名/邮箱
* passWord:密码

```

+ 更新用户信息
```
### 更新用户信息
POST https://www.myindex.top/api/user/v1/updateUser
Content-Type: application/json;charset=UTF-8

{
    "appCode": "",
    "param": {
        "userCode": "",
        "userName": "",
        "birthday": "",
        "verifyCode": "",
        "birthday": "",
        "nickName": "",
        "remark": ""
    }
}

* userCode:用户编码
* userName:用户名
* verifyCode:验证码
  birthday:生日（格式 yyyy-MM-dd 如 2020-12-12）
  nickName:昵称
  remark:备注

```

+ 修改登陆密码
```
### 修改登陆密码
POST https://www.myindex.top/api/user/v1/modifyUserPwd
Content-Type: application/json;charset=UTF-8

{
    "appCode": "",
    "param": {
        "userCode": "",
        "passWord": "",
        "verifyCode": ""
    }
}

* userCode:用户编码
* passWord:修改后的密码
* verifyCode:验证码
```

+ 注销用户
```
### 注销用户
POST https://www.myindex.top/api/user/v1/deleteUser
Content-Type: application/json;charset=UTF-8

{
    "appCode": "",
    "param": {
        "userCode": "",
        "verifyCode": ""
    }
}

* userCode:用户编码
* verifyCode:验证码
```