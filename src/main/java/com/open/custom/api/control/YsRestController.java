package com.open.custom.api.control;

import com.open.custom.api.domain.common.CommonRequest;
import com.open.custom.api.domain.common.CommonResponse;
import com.open.custom.api.exception.BusiException;
import com.open.custom.api.model.OpenAppInfoExample;
import com.open.custom.api.model.YsCustUserInfo;
import com.open.custom.api.service.IYsCustUserInfoService;
import com.open.custom.api.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(description = "英硕主页服务")
@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/ys/v1")
public class YsRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private IYsCustUserInfoService iYsCustUserInfoService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisService redisService;

    @Value("${spring.mail.username}")
    private String mailUserName;


    @ApiOperation(value = "保存测一测的企业信息")
    @PostMapping(value = "/saveTestCustUserInfo")
    public CommonResponse<String> saveTestCustUserInfo(@RequestBody CommonRequest<YsCustUserInfo> commonRequest) {
        CommonResponse response = new CommonResponse();
        YsCustUserInfo param = commonRequest.getParam();

        if (param == null) {
            throw new BusiException("请求参数不能为空");
        }

        String title = "";
        String content = "";

        int i = 0;
        Integer id = param.getId();
        if (id == null || id == 0) {
            // 新增数据
            String companyName = param.getCompanyName();
            if (StringUtils.isEmpty(companyName)) {
                throw new BusiException("公司名称 不能为空");
            }

            String clockNum = param.getClockNum();
            if (StringUtils.isEmpty(clockNum)) {
                throw new BusiException("打卡人数 不能为空");
            }

            String telNum = param.getTelNum();
            if (StringUtils.isEmpty(telNum)) {
                throw new BusiException("联系电话 不能为空");
            }

            param.setState(1);
            param.setCreateDate(new Date());
            i = iYsCustUserInfoService.insertSelective(param);
            response.setData(param);

            title = "有新的测一测公司信息了";
            content = "公司名称：" + companyName + " \n打卡人数：" + clockNum + " \n联系电话：" + telNum;
        } else {
            // 保存数据

            String revName = param.getRevName();
            if (StringUtils.isEmpty(revName)) {
                throw new BusiException("收件人姓名 不能为空");
            }

            String contactNum = param.getContactNum();
            if (StringUtils.isEmpty(contactNum)) {
                throw new BusiException("电话 不能为空");
            }

            String address = param.getAddress();
            if (StringUtils.isEmpty(address)) {
                throw new BusiException("收货地址 不能为空");
            }

            YsCustUserInfo ysCustUserInfo = iYsCustUserInfoService.selectByPrimaryKey(id);
            if (!ysCustUserInfo.getTelNum().equals(param.getTelNum())) {
                throw new BusiException("手机号码不匹配");
            }

            param.setUpdateDate(new Date());
            i = iYsCustUserInfoService.updateByPrimaryKeySelective(param);

            title = "有新的测一测通过并填写邮寄地址了";
            content = "公司名称：" + ysCustUserInfo.getCompanyName() + " \n打卡人数：" + ysCustUserInfo.getClockNum()
                    + "收件人：" + revName + " \n电话：" + contactNum + " \n收货地址：" + address;
        }

        if (i < 1) {
            throw new BusiException("保存失败");
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailUserName);
            message.setTo(mailUserName);
            message.setSubject(title);
            message.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("sendEMail catch Exception {}", e);
        }

        return response;
    }

}
