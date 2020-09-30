package com.open.custom.api.utils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.open.custom.api.exception.BusiException;
import com.open.custom.api.service.RedisService;
import com.sun.javafx.collections.MappingChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class SendMailUtils {


    private static final Logger log = LoggerFactory.getLogger(SendMailUtils.class);

    public static SendMailUtils instance;

    @PostConstruct
    public void init() {
        instance = this;
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleWithFixedDelay(instance.createRunnable(), 0, 1, TimeUnit.SECONDS);
    }

    private SendMailUtils() {

    }


    private static Gson gson = new Gson();

    @Autowired
    private RedisService redisService;

    @Autowired
    private JavaMailSender mailSender;

    private static final String MAIL_MESSAGE = "MAIL_MESSAGE";

    @Value("${spring.mail.username}")
    private String mailUserName;

    public static void addMsg(SimpleMailMessage message) {
        String uuid = UUID.randomUUID().toString();
        message.setFrom(instance.mailUserName);
        instance.redisService.hset(MAIL_MESSAGE, uuid, gson.toJson(message));
    }

//    static {
//        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutor.scheduleWithFixedDelay(instance.createRunnable(), 0, 1, TimeUnit.SECONDS);
//    }

    private Runnable createRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Map<Object, Object> messages = redisService.hmget(MAIL_MESSAGE);
                    if (CollectionUtils.isEmpty(messages)) {
                        return;
                    }
                    messages.forEach((key, value) -> {
                        if (key == null || value == null) {
                            return;
                        }
                        String uuid = (String) key;
                        if (StringUtils.isEmpty(uuid)) {
                            return;
                        }
                        String messageStr = (String) value;
                        if (StringUtils.isEmpty(messageStr)) {
                            return;
                        }
                        SimpleMailMessage message = gson.fromJson(messageStr, SimpleMailMessage.class);
                        String[] to = message.getTo();
                        if (to == null || to.length < 1) {
                            return;
                        }
                        StringBuffer toS = new StringBuffer();
                        for (String item : to) {
                            toS.append(item).append(", ");
                        }
                        log.info("Send Mail to: " + toS.toString().substring(0, toS.toString().lastIndexOf(",")));
                        mailSender.send(message);
                        redisService.hdel(MAIL_MESSAGE, uuid);
                    });
                } catch (Exception e) {
                    log.error("sendEMail catch Exception {}", e);
                }
            }
        };
    }
}
