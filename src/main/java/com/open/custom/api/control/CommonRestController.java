package com.open.custom.api.control;

import com.jcraft.jsch.ChannelSftp;
import com.open.custom.api.config.SFtpConfig;
import com.open.custom.api.domain.common.CommonRequest;
import com.open.custom.api.domain.common.CommonResponse;
import com.open.custom.api.exception.BusiException;
import com.open.custom.api.factory.SftpSessionFactory;
import com.open.custom.api.model.OpenAppInfo;
import com.open.custom.api.model.OpenStaticData;
import com.open.custom.api.service.IOpenAppInfoService;
import com.open.custom.api.service.IOpenStaticDataService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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


    @Autowired
    private IOpenStaticDataService iOpenStaticDataService;

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

    @Value("${custCacheKey.OPEN_APP_INFO}")
    private String OPEN_APP_INFO;

    @Value("${custCacheKey.OPEN_STATIC_DATA}")
    private String OPEN_STATIC_DATA;


//    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})

    @ApiOperation(value = "删除redis数据")
    @GetMapping(value = "/flushRedis")
    public CommonResponse<String> flushRedis(CommonRequest<String> commonRequest) throws IOException {
        CommonResponse<String> commonResponse = new CommonResponse();
        redisService.del(OPEN_APP_INFO, OPEN_STATIC_DATA);
        return commonResponse;
    }


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

    @ApiOperation(value = "获取随机图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "getType", value = "获取方式 随机(random) 最新(lastest)", required = true, dataType = "String"),
            @ApiImplicitParam(name = "picType", value = "图片类型 原图(source) 电脑端(pc) 手机端(phone)", required = true, dataType = "String")
    })
    @GetMapping(value = "/getPicture/{getType}/{picType}")
    public CommonResponse<String> getPicture(
            @PathVariable(value = "getType", required = true) String getType,
            @PathVariable(value = "picType", required = true) String picType,
//            @RequestParam(value = "picType", required = false) String picType,
            HttpServletResponse response) throws IOException {
        CommonResponse<String> commonResponse = new CommonResponse();

        List<OpenStaticData> openStaticData = iOpenStaticDataService.getStaticDataByCodeType("BY_IMG");
        if (!CollectionUtils.isEmpty(openStaticData)) {

            Collections.sort(openStaticData, new Comparator<OpenStaticData>() {
                @Override
                public int compare(OpenStaticData o1, OpenStaticData o2) {
                    return -(o1.getSort() - o2.getSort());
                }
            });

            String returnUrl = "";
            int size = openStaticData.size();
            if ("random".equals(getType)) {
                // 获取 1 - size 的随机数
                int random = (int) (1 + Math.random() * size);
                returnUrl = openStaticData.get(random - 1).getCodeValue();
            }
            if ("lastest".equals(getType)) {
                returnUrl = openStaticData.get(0).getCodeValue();
            }

            if (!StringUtils.isEmpty(returnUrl)) {
                String img1920 = returnUrl.substring(0, returnUrl.indexOf("&"));

                String img1366 = img1920.replaceAll("1920x1080", "1366x768");
                String img1080 = img1920.replaceAll("1920x1080", "1080x1920");
                String imgUHD = img1920.replaceAll("1920x1080", "UHD");

                returnUrl = img1920;
                if ("source".equals(picType)) {
                    returnUrl = imgUHD;
                }
                if ("pc".equals(picType)) {
                    returnUrl = img1920;
                }
                if ("phone".equals(picType)) {
                    returnUrl = img1080;
                }
            }

            response.sendRedirect(returnUrl);
        }

        // https://cn.bing.com/th?id=OHR.BorrowingDays_ZH-CN3558219803_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp // 原链接

        // https://cn.bing.com/th?id=OHR.BorrowingDays_ZH-CN3558219803_1366x768.jpg
        // https://cn.bing.com/th?id=OHR.BorrowingDays_ZH-CN3558219803_1920x1080.jpg // 桌面版
        // https://cn.bing.com/th?id=OHR.BorrowingDays_ZH-CN3558219803_1080x1920.jpg // 手机
        // https://cn.bing.com/th?id=OHR.BorrowingDays_ZH-CN3558219803_UHD.jpg // 原图


        return commonResponse;


//        try (InputStream inputStream = new URL("https://www.myindex.top/file/myfile/txy.png").openConnection().getInputStream();
//             ServletOutputStream outputStream = response.getOutputStream();) {
//            if (inputStream != null) {
//                int len;
//                byte[] buffer = new byte[1024];
//                while ((len = inputStream.read(buffer)) > 0) {
//                    outputStream.write(buffer, 0, len);
//                }
//            }
//        } catch (IOException e2) {
//            e2.printStackTrace();
//        } catch (Exception e3) {
//            e3.printStackTrace();
//        }
    }
}
