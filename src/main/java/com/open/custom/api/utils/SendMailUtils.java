package com.open.custom.api.utils;

import com.google.gson.Gson;
import com.open.custom.api.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description: 发送邮件
 * 1.最多重试3次，如果都是失败后，直接删除该发送请求
 * 2.redis读取加锁，避免部署多个时，导致重复发送邮件
 * <p>
 * Author: huxintao
 * Date: 2020-09-30
 *
 * @param null
 * @Return
 */
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
    private static final String MAIL_MESSAGE_LOCK = "MAIL_MESSAGE_LOCK";

    @Value("${spring.mail.username}")
    private String mailUserName;


    private static Map<String, Integer> failedMsg = new ConcurrentHashMap<>();


    public static void addMsg(SimpleMailMessage message) {
        String uuid = UUID.randomUUID().toString();
        message.setFrom(instance.mailUserName);
        instance.redisService.hset(MAIL_MESSAGE, uuid, gson.toJson(message));
    }

    private Runnable createRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                boolean lock = false;
                try {
                    // 获取锁失败
                    lock = redisService.getLock(MAIL_MESSAGE_LOCK, 2000);
                    if (!lock) {
                        // 获取锁不成功
                        log.info("获取锁失败: " + MAIL_MESSAGE_LOCK);
                        return;
                    }
//                    log.info("获取锁成功: " + MAIL_MESSAGE_LOCK);

                    Map<Object, Object> messages = redisService.hmget(MAIL_MESSAGE);
                    if (CollectionUtils.isEmpty(messages)) {
                        return;
                    }
                    messages.forEach((key, value) -> {
                        String lockId = "";
                        try {
                            if (key == null || value == null) {
                                return; // 相当于 continue
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
                            int time = 0;
                            try {
                                mailSender.send(message);
                            } catch (MailException e) {
                                log.error("sendEMail catch MailException {}", e);
                                time = failedMsg.get(lockId) + 1;
                                failedMsg.put(lockId, time);
                            } finally {
                                // 成功 或者 已经失败 3 次
                                if (time == 0 || time >= 3) {
                                    redisService.hdel(MAIL_MESSAGE, uuid);
                                }
                            }
                        } catch (Exception e) {
                            log.error("sendEMail catch Exception {}", e);
                        }
                    });
                } catch (Exception e) {
                    log.error("sendEMail catch Exception {}", e);
                } finally {
                    if (lock) {
                        // 拿到锁 完成后就需要释放锁
//                        log.info("释放锁: " + MAIL_MESSAGE_LOCK);
                        redisService.releaseLock(MAIL_MESSAGE_LOCK);
                    }
                }
            }
        };
    }
}
