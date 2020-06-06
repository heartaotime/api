package com.open.custom.api.app.control;

import com.open.custom.api.app.model.OpenAppInfo;
import com.open.custom.api.app.model.OpenAppInfoExample;
import com.open.custom.api.app.sevice.IOpenAppInfoService;
import com.open.custom.api.bean.CommonRequest;
import com.open.custom.api.bean.CommonResponse;
import com.open.custom.api.exception.BusiException;
import com.open.custom.api.service.RedisService;
import com.open.custom.api.user.model.OpenUserInfo;
import com.open.custom.api.user.model.extend.OpenUserInfoExtend;
import com.open.custom.api.user.sevice.IOpenUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/app/v1")
public class AppRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private IOpenAppInfoService iOpenAppInfoService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisService redisService;

    @Value("${spring.mail.username}")
    private String mailUserName;

    @Value("${custCacheKey.OPEN_APP_INFO}")
    private String OPEN_APP_INFO;

    @PostMapping(value = "/saveApp")
    public CommonResponse<String> saveApp(@RequestBody CommonRequest<OpenAppInfo> commonRequest) {
        CommonResponse response = new CommonResponse();
        OpenAppInfo param = commonRequest.getParam();

        if (param == null) {
            throw new BusiException("请求参数不能为空");
        }

        String email = param.getEmail();
        if (StringUtils.isEmpty(email)) {
            throw new BusiException("email不能为空");
        }

        String appCode = UUID.randomUUID().toString().replaceAll("-", "");
        String appSecret = UUID.randomUUID().toString().replaceAll("-", "");

        param.setAppCode(appCode);
        param.setAppSecret(appSecret);
        param.setState(1);
        param.setCreateDate(new Date());
        int i = iOpenAppInfoService.insertSelective(param);

        if (i < 1) {
            throw new BusiException("保存失败");
        }

        // 更新 redis 信息
        redisService.del(OPEN_APP_INFO);

        OpenAppInfoExample example = new OpenAppInfoExample();
        OpenAppInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(1);
        redisService.set(OPEN_APP_INFO, iOpenAppInfoService.selectByExample(example));

        return response;
    }

}
