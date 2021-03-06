# 开放接口服务

## 使用须知

+ 接口免费提供服务，并且支持跨域调用，请合理使用
+ 为了防止接口滥用，调用大部分接口都需要提供 `appCode` ，所以正式使用接口前先邮件 `heartaotime@foxmail.com` ，注册一个应用
+ 邮件中主要需要说明的模板如下（没有的填写暂无）：
  + 1.应用名称：
  + 2.应用描述：
  + 3.地址：
  + 4.你的邮箱：
  + 5.其他：
+ 如果你感觉这个功能还行，欢迎捐赠哦：
  + 支付宝：![支付宝](https://www.myindex.top/file/myfile/zfb.png "支付宝") 微信：![微信](https://www.myindex.top/file/myfile/wx.png "微信")

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
### 目前所有接口列表
- 校验用户名或邮箱是否已注册
- 发送验证码邮件
- 用户注册
- 用户登陆
- 更新用户信息
- 修改登陆密码
- 用户注销
- 获取用户列表（分页）
- 设置用户拓展信息
- 获取用户拓展信息
- 文件上传
- 获取图片

- 后续接口开发计划：天气接口，图片汇总接口，新闻接口，
古诗词接口，每日一句接口，随机返回图片接口，基于appCode的全局静态配置接口，
新用户注册邮件通知到appCode的持有者 等功能... 

### 接口详细调用说明 （参数标记 * 为必传）

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


+ 获取用户
```
### 获取用户
POST https://www.myindex.top/api/user/v1/getUserList
Content-Type: application/json;charset=UTF-8

{
    "appCode": "",
    "param": {
        "userCode": "",
        "userName": ""
    },
    "pageNum": "1",
    "pageSize": "10"
}

  userCode:用户编码
  userName:用户名
  pageNum:页码
  pageSize:每页显示多少条
```

+ 设置用户拓展信息
```
### 设置用户拓展
POST https://www.myindex.top/api/user/v1/setUserExtInfo
Content-Type: application/json;charset=UTF-8

{
    "appCode": "",
    "param": {
        "userCode": "",
        "userSet": "",
        "userSet1": "",
        "userSet2": ""
    }
}

* userCode:用户编码
* userSet:用户设置（支持字符串，比如json格式的字符串 {\"test\": \"123\"} ）
  userSet1:用户设置1（支持字符串，比如json格式的字符串 {\"test\": \"123\"} ）
  userSet2:用户设置2（支持字符串，比如json格式的字符串 {\"test\": \"123\"} ）
```

+ 获取用户拓展信息
```
### 获取用户拓展
POST https://www.myindex.top/api/user/v1/getUserExtInfo
Content-Type: application/json;charset=UTF-8

{
    "appCode": "",
    "param": {
        "userCode": ""
    }
}

* userCode:用户编码
```

+ 文件上传
```
### 文件上传
POST https://www.myindex.top/api/common/v1/upload
Content-Type: multipart/form-data;charset=UTF-8

* appCode:应用编码
* file:文件
```
![文件上传请求示例](https://www.myindex.top/file/myfile/uploadapi.png "文件上传请求示例")

+ 获取图片
```
### 获取图片
GET https://www.myindex.top/api/common/v1/getPicture/{getType}/{picType}

* getType 获取方式 随机(random) 最新(lastest)
* picType 图片类型 原图(source) 电脑端(pc) 手机端(phone)

示例URL https://www.myindex.top/api/common/v1/getPicture/random/phone

```