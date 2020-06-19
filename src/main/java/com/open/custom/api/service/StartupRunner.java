package com.open.custom.api.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.open.custom.api.model.OpenStaticData;
import com.open.custom.api.model.OpenStaticDataExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(value = 1)
public class StartupRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);

    @Autowired
    private IOpenStaticDataService iOpenStaticDataService;

    private static final Gson gson = new Gson();

    @Autowired
    private RedisService redisService;

    @Value("${custCacheKey.OPEN_STATIC_DATA}")
    private String OPEN_STATIC_DATA;


    // 获取 静态数据 并保存到redis
    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();
        log.info("Enter StartupRunner.getOpenStaticData, start>>>");
        try {
            iOpenStaticDataService.saveData2Redis();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("StartupRunner.getOpenStaticData catch Exception, ", e);
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        log.info("Exit StartupRunner.getOpenStaticData, duration = " + duration / 1000 + " s");
    }
}