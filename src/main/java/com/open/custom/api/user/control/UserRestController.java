package com.open.custom.api.user.control;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.open.custom.api.app.model.OpenAppInfo;
import com.open.custom.api.app.sevice.IOpenAppInfoService;
import com.open.custom.api.bean.CommonRequest;
import com.open.custom.api.bean.CommonResponse;
import com.open.custom.api.exception.BusiException;
import com.open.custom.api.service.RedisService;
import com.open.custom.api.user.model.OpenUserInfo;
import com.open.custom.api.user.model.OpenUserInfoExample;
import com.open.custom.api.user.model.OpenUserInfoExt;
import com.open.custom.api.user.model.OpenUserInfoExtWithBLOBs;
import com.open.custom.api.user.model.bean.OpenUserInfoBean;
import com.open.custom.api.user.model.bean.OpenUserInfoExtBean;
import com.open.custom.api.user.model.extend.OpenUserInfoExtend;
import com.open.custom.api.user.sevice.IOpenUserInfoExtService;
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

import java.util.ArrayList;
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
    private IOpenUserInfoExtService iOpenUserInfoExtService;

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
        userInfo.setBirthday(DateUtils.parseDate(param.getBirthdayStr(), DateUtils.YYYY_MM_DD));
        userInfo.setNickName(param.getNickName());
        userInfo.setRemark(param.getRemark());
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

                OpenUserInfoExample example = new OpenUserInfoExample();
                OpenUserInfoExample.Criteria criteria = example.createCriteria();
                criteria.andStateEqualTo(1);
                criteria.andAppCodeEqualTo(appCode);
                int allCount = iOpenUserInfoService.countByExample(example);

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
                                "\n    总注册用户数：" + allCount);
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

    @PostMapping(value = "/getUserList")
    public CommonResponse<PageInfo<OpenUserInfoBean>> getUserList(@RequestBody CommonRequest<OpenUserInfo> commonRequest) {
        CommonResponse<PageInfo<OpenUserInfoBean>> response = new CommonResponse();
        String appCode = commonRequest.getAppCode();

        OpenUserInfo param = commonRequest.getParam();
        String userCode = param.getUserCode();
        String userName = param.getUserName();
        Boolean pageFlag = commonRequest.getPageFlag();
        Integer pageNum = commonRequest.getPageNum();
        Integer pageSize = commonRequest.getPageSize();

        OpenUserInfoExample example = new OpenUserInfoExample();
        OpenUserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andAppCodeEqualTo(appCode);
        if (!StringUtils.isEmpty(userCode)) {
            criteria.andUserCodeEqualTo(userCode);
        }
        if (!StringUtils.isEmpty(userName)) {
            criteria.andUserNameEqualTo(userName);
        }

        example.setOrderByClause(" CREATE_DATE DESC, UPDATE_DATE DESC ");

        if (pageFlag) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<OpenUserInfo> openUserInfos = iOpenUserInfoService.selectByExample(example);
        List<OpenUserInfoBean> openUserInfoBeans = new ArrayList<>();
        if (!CollectionUtils.isEmpty(openUserInfos)) {
            for (OpenUserInfo openUserInfo : openUserInfos) {
                openUserInfoBeans.add(convertUser(openUserInfo));
            }

            // 取分页信息
            PageInfo<OpenUserInfo> pageInfo = new PageInfo<OpenUserInfo>(openUserInfos);
            PageInfo<OpenUserInfoBean> pageInfoRes = new PageInfo<OpenUserInfoBean>(openUserInfoBeans);
            pageInfoRes.setTotal(pageInfo.getTotal());
            pageInfoRes.setPageNum(pageInfo.getPageNum());
            pageInfoRes.setPageSize(pageInfo.getPageSize());
            pageInfoRes.setSize(pageInfo.getSize());
            pageInfoRes.setStartRow(pageInfo.getStartRow());
            pageInfoRes.setEndRow(pageInfo.getEndRow());
            pageInfoRes.setPages(pageInfo.getPages());
            pageInfoRes.setPrePage(pageInfo.getPrePage());
            pageInfoRes.setNextPage(pageInfo.getNextPage());
            pageInfoRes.setIsFirstPage(pageInfo.isIsFirstPage());
            pageInfoRes.setIsLastPage(pageInfo.isIsLastPage());
            pageInfoRes.setHasPreviousPage(pageInfo.isHasPreviousPage());
            pageInfoRes.setHasNextPage(pageInfo.isHasNextPage());
            pageInfoRes.setNavigatePages(pageInfo.getNavigatePages());
            pageInfoRes.setNavigatepageNums(pageInfo.getNavigatepageNums());
            pageInfoRes.setNavigateFirstPage(pageInfo.getNavigateFirstPage());
            pageInfoRes.setNavigateLastPage(pageInfo.getNavigateLastPage());
            response.setData(pageInfoRes);
        }

        return response;
    }


    @PostMapping(value = "/setUserExtInfo")
    public CommonResponse<String> setUserExtInfo(@RequestBody CommonRequest<OpenUserInfoExtWithBLOBs> commonRequest) {
        CommonResponse<String> response = new CommonResponse();
        String appCode = commonRequest.getAppCode();

        OpenUserInfoExtWithBLOBs param = commonRequest.getParam();

        String userCode = param.getUserCode();
        String userSet = param.getUserSet();
        String userSet1 = param.getUserSet1();
        String userSet2 = param.getUserSet2();
        String remark = param.getRemark();

        if (StringUtils.isEmpty(userCode) || StringUtils.isEmpty(userSet)) {
            throw new BusiException("请求参数不能为空");
        }

        OpenUserInfo userInfo = iOpenUserInfoService.getUserByCode(userCode);
        if (userInfo == null) {
            throw new BusiException("该用户不存在[" + userCode + "]");
        }

        int i = 0;

        OpenUserInfoExtWithBLOBs upUser = new OpenUserInfoExtWithBLOBs();
        upUser.setUserSet(userSet);
        upUser.setUserSet1(userSet1);
        upUser.setUserSet2(userSet2);
        upUser.setRemark(remark);

        OpenUserInfoExt userInfoExt = iOpenUserInfoExtService.getUserExtInfoByCode(userCode);
        if (userInfoExt == null) {
            upUser.setUserId(userInfo.getId());
            upUser.setUserCode(userCode);
            upUser.setState(1);
            upUser.setCreateDate(new Date());
            i = iOpenUserInfoExtService.insertSelective(upUser);
        } else {
            upUser.setId(userInfoExt.getId());
            upUser.setUpdateDate(new Date());
            i = iOpenUserInfoExtService.updateByPrimaryKeySelective(upUser);
        }
        if (i < 1) {
            throw new BusiException("更新/新增失败");
        }
        return response;
    }

    @PostMapping(value = "/getUserExtInfo")
    public CommonResponse<OpenUserInfoExtBean> getUserExtInfo(@RequestBody CommonRequest<OpenUserInfoExt> commonRequest) {
        CommonResponse<OpenUserInfoExtBean> response = new CommonResponse();
        String appCode = commonRequest.getAppCode();

        OpenUserInfoExt param = commonRequest.getParam();

        String userCode = param.getUserCode();

        if (StringUtils.isEmpty(userCode)) {
            throw new BusiException("请求参数不能为空");
        }


        OpenUserInfoExtWithBLOBs userInfoExt = iOpenUserInfoExtService.getUserExtInfoWithBLOBsByCode(userCode);
        if (userInfoExt != null) {
            response.setData(convertUser(userInfoExt));
        }
        return response;
    }

    private static OpenUserInfoExtBean convertUser(OpenUserInfoExtWithBLOBs data) {
        OpenUserInfoExtBean bean = new OpenUserInfoExtBean();
        BeanUtils.copyProperties(data, bean);
        bean.setCreateDate(DateUtils.getDateStr(data.getCreateDate()));
        bean.setUpdateDate(DateUtils.getDateStr(data.getUpdateDate()));
        return bean;
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
