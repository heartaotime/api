package com.open.custom.api.user.control;

import com.open.custom.api.app.model.OpenAppInfo;
import com.open.custom.api.app.sevice.IOpenAppInfoService;
import com.open.custom.api.bean.CommonRequest;
import com.open.custom.api.bean.CommonResponse;
import com.open.custom.api.exception.BusiException;
import com.open.custom.api.service.RedisService;
import com.open.custom.api.user.model.OpenUserInfo;
import com.open.custom.api.user.model.OpenUserInfoExample;
import com.open.custom.api.user.model.bean.OpenUserInfoBean;
import com.open.custom.api.user.model.extend.OpenUserInfoExtend;
import com.open.custom.api.user.sevice.IOpenUserInfoService;
import com.open.custom.api.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user/v1")
public class UserRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private IOpenUserInfoService iOpenUserInfoService;

    @Autowired
    private IOpenAppInfoService iOpenAppInfoService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisService redisService;

    @Value("${spring.mail.username}")
    private String mailUserName;

    @PostMapping(value = "/checkUserExist")
    public CommonResponse<String> checkUserExist(@RequestBody CommonRequest<OpenUserInfo> commonRequest) {
        CommonResponse response = new CommonResponse();
        String appCode = commonRequest.getAppCode();


        OpenUserInfo param = commonRequest.getParam();
        String userName = param.getUserName();
        String email = param.getEmail();

        if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(email)) {
            throw new BusiException("userName / email 至少传入一个");
        }

        if (!StringUtils.isEmpty(userName)) {
            Integer count = iOpenUserInfoService.checkUserExist(appCode, userName);
            if (count == null || count > 0) {
                throw new BusiException("该用户已存在[" + userName + "]");
            }
        } else if (!StringUtils.isEmpty(email)) {
            Integer count = iOpenUserInfoService.checkUserExist(appCode, email);
            if (count == null || count > 0) {
                throw new BusiException("该邮箱已存在[" + email + "]");
            }
        }
        return response;
    }


    @PostMapping(value = "/sendEMail")
    public CommonResponse sendEMail(@RequestBody CommonRequest<OpenUserInfo> commonRequest) {
        CommonResponse response = new CommonResponse();

        String appCode = commonRequest.getAppCode();

        OpenAppInfo openAppInfo = iOpenAppInfoService.assertAppCode(appCode);
        String appName = openAppInfo.getAppName();

        OpenUserInfo param = commonRequest.getParam();

        String email = param.getEmail();

        if (StringUtils.isEmpty(email)) {
            throw new BusiException("请求参数不能为空");
        }

        String verifyCode = "";
        // 生成4位随机数
        for (int i = 0; i < 4; i++) {
            int tmp = (int) (Math.random() * 10);
            verifyCode += tmp;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailUserName);
            message.setTo(email);
            message.setSubject("开放API平台验证码，来自应用[" + appName + "]");
            message.setText("你好：验证码为 " + verifyCode + " (该验证码5分钟内有效)");
            mailSender.send(message);

            redisService.set(appCode + "|" + email, verifyCode, 5 * 60);

        } catch (Exception e) {
            log.error("sendEMail catch Exception {}", e);
            throw new BusiException("发送验证码失败");
        }
        return response;
    }


    @PostMapping(value = "/registUser")
    public CommonResponse registUser(@RequestBody CommonRequest<OpenUserInfoExtend> commonRequest) {
        CommonResponse response = new CommonResponse();
        String appCode = commonRequest.getAppCode();

        OpenUserInfoExtend param = commonRequest.getParam();

        String userName = param.getUserName();
        String passWord = param.getPassWord();
        String email = param.getEmail();
        String verifyCode = param.getVerifyCode();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord) || StringUtils.isEmpty(email) || StringUtils.isEmpty(verifyCode)) {
            throw new BusiException("请求参数不能为空");
        }

        Integer count = iOpenUserInfoService.checkUserExist(appCode, userName);
        if (count == null || count > 0) {
            throw new BusiException("该用户名已存在[" + userName + "]");
        }

        count = iOpenUserInfoService.checkUserExist(appCode, email);
        if (count == null || count > 0) {
            throw new BusiException("该邮箱已存在[" + email + "]");
        }

        // 校验 验证码
        Object verifyCodeObj = redisService.get(appCode + "|" + email);
        if (verifyCodeObj == null || !verifyCodeObj.toString().equals(verifyCode.trim())) {
            throw new BusiException("邮箱验证码有误！");
        }


        OpenUserInfoExtend userInfo = new OpenUserInfoExtend();
        String userCode = UUID.randomUUID().toString().replaceAll("-", "");
        userInfo.setUserCode(userCode);
        userInfo.setAppCode(appCode);
        userInfo.setUserName(userName);
        userInfo.setPassWord(passWord);
        userInfo.setEmail(email);
        userInfo.setCreateDate(new Date());
        userInfo.setState(1);
        int save = iOpenUserInfoService.insertSelective(userInfo);
        if (save > 0) {
            // 验证完成后删除 验证码
            redisService.del(appCode + "|" + email);

            OpenAppInfo openAppInfo = iOpenAppInfoService.assertAppCode(appCode);

            // 发送邮件通知管理员新用户注册
            try {
                String appName = openAppInfo.getAppName();

                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(mailUserName);
                message.setTo(mailUserName);
                message.setSubject("开放API平台新用户注册通知，来自应用[" + appName + "]");
                message.setText(
                        "你好：" +
                                "\n    有新用户注册了" +
                                "\n    用户名：" + userInfo.getUserName() +
                                "\n    邮箱：" + userInfo.getEmail() +
                                "\n    注册时间：" + DateUtils.getDateStr(userInfo.getCreateDate()) +
                                "\n    总注册用户数：" + userInfo.getId());
                mailSender.send(message);
            } catch (Exception e) {
                log.error("sendEMail catch Exception {}", e);
            }
        } else {
            throw new BusiException("新增用户失败！");
        }
        return response;
    }


    @PostMapping(value = "/updateUser")
    public CommonResponse<OpenUserInfoBean> updateUser(@RequestBody CommonRequest<OpenUserInfoBean> commonRequest) {
        CommonResponse<OpenUserInfoBean> response = new CommonResponse();
        String appCode = commonRequest.getAppCode();

        OpenUserInfoBean param = commonRequest.getParam();
        String userCode = param.getUserCode();
        String verifyCode = param.getVerifyCode();

        // 可选参数
        String userName = param.getUserName();
        String nickName = param.getNickName();
        String birthday = param.getBirthday();
        String remark = param.getRemark();

        if (StringUtils.isEmpty(userCode) || StringUtils.isEmpty(verifyCode)) {
            throw new BusiException("请求参数不能为空");
        }
        OpenUserInfo userInfo = iOpenUserInfoService.getUserByCode(userCode);
        if (userInfo == null) {
            throw new BusiException("该用户不存在[" + userCode + "]");
        }

        String userNameOrg = userInfo.getUserName();
        String emailOrg = userInfo.getEmail();

        if (!StringUtils.isEmpty(userName) && !userName.equals(userNameOrg)) {
            // 用户名不相等的时候，校验是否存在
            Integer count = iOpenUserInfoService.checkUserExist(appCode, userName);
            if (count == null || count > 0) {
                throw new BusiException("该用户名已存在[" + userName + "]");
            }
        }

//        if (!email.equals(emailOrg)) {
//            // 邮箱地址不相等的时候，校验是否存在
//            Integer count = iOpenUserInfoService.checkUserExist(appCode, email);
//            if (count == null || count > 0) {
//                throw new BusiException("该邮箱已存在[" + email + "]");
//            }
//        }


        // 校验 验证码
        Object verifyCodeObj = redisService.get(appCode + "|" + emailOrg);
        if (verifyCodeObj == null || !verifyCodeObj.toString().equals(verifyCode.trim())) {
            throw new BusiException("邮箱验证码有误！");
        }

        OpenUserInfo userInfoU = new OpenUserInfo();
        userInfoU.setId(userInfo.getId());
        userInfoU.setUserName(userName);
        userInfoU.setBirthday(DateUtils.parseDate(birthday, DateUtils.YYYY_MM_DD));
        userInfoU.setNickName(nickName);
        userInfoU.setRemark(remark);
        userInfoU.setUpdateDate(new Date());
        int count = iOpenUserInfoService.updateByPrimaryKeySelective(userInfoU);
        if (count > 0) {
            OpenUserInfo data = iOpenUserInfoService.getUserByCode(userCode);
            response.setData(convertUser(data));

            redisService.del(appCode + "|" + emailOrg);
        } else {
            throw new BusiException("更新用户失败！");
        }
        return response;
    }


    @PostMapping(value = "/modifyUserPwd")
    public CommonResponse<String> modifyUserPwd(@RequestBody CommonRequest<OpenUserInfoExtend> commonRequest) {
        CommonResponse<String> response = new CommonResponse();
        String appCode = commonRequest.getAppCode();

        OpenUserInfoExtend param = commonRequest.getParam();

        String userCode = param.getUserCode();
        String passWord = param.getPassWord();
        String verifyCode = param.getVerifyCode();
        if (StringUtils.isEmpty(userCode) || StringUtils.isEmpty(passWord) || StringUtils.isEmpty(verifyCode)) {
            throw new BusiException("请求参数不能为空");
        }

        OpenUserInfo userInfo = iOpenUserInfoService.getUserByCode(userCode);
        if (userInfo == null) {
            throw new BusiException("该用户不存在[" + userCode + "]");
        }

        String email = userInfo.getEmail();
        // 校验 验证码
        Object verifyCodeObj = redisService.get(appCode + "|" + email);
        if (verifyCodeObj == null || !verifyCodeObj.toString().equals(verifyCode.trim())) {
            throw new BusiException("邮箱验证码有误！");
        }


        OpenUserInfo userInfoU = new OpenUserInfo();
        userInfoU.setId(userInfo.getId());
        userInfoU.setPassWord(passWord);
        userInfoU.setUpdateDate(new Date());
        int count = iOpenUserInfoService.updateByPrimaryKeySelective(userInfoU);
        if (count > 0) {
            redisService.del(appCode + "|" + email);
        } else {
            throw new BusiException("修改用户密码失败！");
        }
        return response;
    }

    @PostMapping(value = "/deleteUser")
    public CommonResponse<String> deleteUser(@RequestBody CommonRequest<OpenUserInfoBean> commonRequest) {
        CommonResponse<String> response = new CommonResponse();
        String appCode = commonRequest.getAppCode();

        OpenUserInfoBean param = commonRequest.getParam();

        String userCode = param.getUserCode();
        String verifyCode = param.getVerifyCode();
        if (StringUtils.isEmpty(userCode) || StringUtils.isEmpty(verifyCode)) {
            throw new BusiException("请求参数不能为空");
        }

        OpenUserInfo userInfo = iOpenUserInfoService.getUserByCode(userCode);
        if (userInfo == null) {
            throw new BusiException("该用户不存在[" + userCode + "]");
        }

        String email = userInfo.getEmail();
        // 校验 验证码
        Object verifyCodeObj = redisService.get(appCode + "|" + email);
        if (verifyCodeObj == null || !verifyCodeObj.toString().equals(verifyCode.trim())) {
            throw new BusiException("邮箱验证码有误！");
        }


        OpenUserInfo userInfoU = new OpenUserInfo();
        userInfoU.setId(userInfo.getId());
        userInfoU.setState(0);
        userInfoU.setUpdateDate(new Date());
        int count = iOpenUserInfoService.updateByPrimaryKeySelective(userInfoU);
        if (count > 0) {
            redisService.del(appCode + "|" + email);
        } else {
            throw new BusiException("注销用户失败！");
        }
        return response;
    }


    @PostMapping(value = "/loginUser")
    public CommonResponse<OpenUserInfoBean> loginUser(@RequestBody CommonRequest<OpenUserInfo> commonRequest) {
        CommonResponse<OpenUserInfoBean> response = new CommonResponse();
        String appCode = commonRequest.getAppCode();

        OpenUserInfo param = commonRequest.getParam();

        String userName = param.getUserName();
        String passWord = param.getPassWord();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            throw new BusiException("请求参数不能为空");
        }

        OpenUserInfo data = iOpenUserInfoService.login(appCode, userName);
        if (data == null) {
            throw new BusiException("该账号不存在");
        }
        if (!passWord.equals(data.getPassWord())) {
            throw new BusiException("密码错误");
        }

        response.setData(convertUser(data));
        return response;
    }


    private static OpenUserInfoBean convertUser(OpenUserInfo data) {
        OpenUserInfoBean bean = new OpenUserInfoBean();
        BeanUtils.copyProperties(data, bean);
        bean.setCreateDate(DateUtils.getDateStr(data.getCreateDate()));
        bean.setUpdateDate(DateUtils.getDateStr(data.getUpdateDate()));
        bean.setBirthday(DateUtils.getDateStr(data.getBirthday(), DateUtils.YYYY_MM_DD));
        return bean;
    }

}
