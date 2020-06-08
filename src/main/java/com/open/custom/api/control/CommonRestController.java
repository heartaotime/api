package com.open.custom.api.control;

import com.jcraft.jsch.ChannelSftp;
import com.open.custom.api.bean.CommonResponse;
import com.open.custom.api.config.SFtpConfig;
import com.open.custom.api.exception.BusiException;
import com.open.custom.api.factory.SftpSessionFactory;
import com.open.custom.api.model.OpenAppInfo;
import com.open.custom.api.service.IOpenAppInfoService;
import com.open.custom.api.service.RedisService;
import com.open.custom.api.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Api(description = "通用服务")
@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = "/common/v1")
public class CommonRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private IOpenAppInfoService iOpenAppInfoService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SftpSessionFactory sftpSessionFactory;

    @Value("${spring.mail.username}")
    private String mailUserName;

    @Value("${uploadpath.open}")
    private String openPath;

    @Value("${uploadpath.opentemp}")
    private String openTempPath;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${userhome}")
    private String userHome;

    @Autowired
    private SFtpConfig sFtpConfig;


    @ApiOperation(value = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appCode", value = "应用编码", required = true, dataType = "String")
    })
    @PostMapping(value = "/upload")
    public CommonResponse<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("appCode") String appCode) throws Exception {
        CommonResponse<String> response = new CommonResponse();

        if (StringUtils.isEmpty(appCode)) {
            throw new BusiException("appCode不能为空");
        }
        OpenAppInfo openAppInfo = iOpenAppInfoService.assertAppCode(appCode);

        if (file.isEmpty()) {
            throw new BusiException("文件不能为空");
        }

        String path = openPath + appCode + "/" + DateUtils.getCurDateStr(DateUtils.YYYYMMDD);

        String originalFilename = file.getOriginalFilename();

        String type = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + type;
        ChannelSftp session = sftpSessionFactory.getSession(path);
        session.put(file.getInputStream(), fileName);
        response.setData(baseUrl + sFtpConfig.getBasePath() + path + "/" + fileName);

        return response;
    }
}
