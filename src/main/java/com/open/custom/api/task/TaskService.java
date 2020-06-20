package com.open.custom.api.task;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.open.custom.api.model.OpenStaticData;
import com.open.custom.api.model.OpenStaticDataExample;
import com.open.custom.api.model.OpenUserInfo;
import com.open.custom.api.service.IOpenStaticDataService;
import com.open.custom.api.service.RedisService;
import com.open.custom.api.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private IOpenStaticDataService iOpenStaticDataService;

    private static final Gson gson = new Gson();

    @Autowired
    private RedisService redisService;

    @Value("${custCacheKey.OPEN_STATIC_DATA}")
    private String OPEN_STATIC_DATA;



    // 获取 必应每日一图
    @Async
//    @Scheduled(initialDelay = 1000, fixedRate = 24 * 60 * 60 * 1000) // 第一次延迟initialDelay秒后执行，之后按每fixedRate秒执行一次。
    @Scheduled(cron = "0 10 0 * * ?") // 每天0：10执行一次
    public void getBYRT() {
        long start = System.currentTimeMillis();
        log.info("Enter TaskService.getBYRT, start>>>");
        try {
            String url = "https://cn.bing.com/HPImageArchive.aspx/";
            url += "?format=js&pid=hp&video=0&idx=0&n=1&nc=" + new Date().getTime();

            Map<String, Object> jRes = HttpClientUtil.getJsonMap(url);


            if (CollectionUtils.isEmpty(jRes) || !jRes.containsKey("images")) {
                return;
            }

            Object images = jRes.get("images");

            if (images == null) {
                return;
            }

            List<Map<String, String>> imageList = (List<Map<String, String>>) images;

            if (CollectionUtils.isEmpty(imageList)) {
                return;
            }

            Map<String, String> res = imageList.get(0);
            if (CollectionUtils.isEmpty(res)) {
                return;
            }

            String basicUrl = "https://cn.bing.com";

            String imgUrl = basicUrl + res.get("url");
            String urlbase = res.get("urlbase");
            String copyright = res.get("copyright");
            String copyrightlink = basicUrl + res.get("copyrightlink");
            String quiz = res.get("quiz");
            String enddate = res.get("enddate");

            OpenStaticData openStaticData = new OpenStaticData();
            openStaticData.setCodeType("BY_IMG");
            openStaticData.setCodeName(enddate);
            openStaticData.setCodeValue(imgUrl);
            openStaticData.setCodeDesc(copyright);
            openStaticData.setSort(Integer.parseInt(enddate));
            openStaticData.setState(1);
            openStaticData.setExt1(copyrightlink);
            iOpenStaticDataService.insertSelective(openStaticData);

            iOpenStaticDataService.saveData2Redis();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("TaskService.getBYRT catch Exception, ", e);
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("Exit TaskService.getBYRT, duration = " + duration / 1000 + " s");
    }

}
