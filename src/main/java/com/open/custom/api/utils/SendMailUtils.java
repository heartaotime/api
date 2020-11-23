package com.open.custom.api.utils;

import com.google.gson.Gson;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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
        scheduledExecutor.scheduleWithFixedDelay(instance.createRunnable(), 5, 1, TimeUnit.SECONDS);
    }

    private SendMailUtils() {

    }


    private static Gson gson = new Gson();


    @Autowired
    private JavaMailSender mailSender;

//    @Autowired
//    private RedissonClient redissonClient;

    @Autowired
    private RedissonClient redissonClient;


    private static final String MAIL_MESSAGE = "MAIL_MESSAGE";
    private static final String MAIL_MESSAGE_LOCK = "MAIL_MESSAGE_LOCK";

    @Value("${spring.mail.username}")
    private String mailUserName;


    public static void addMsg(SimpleMailMessage message) {
        String uuid = UUID.randomUUID().toString();
        message.setFrom(instance.mailUserName);
        RMap<Object, Object> messages = instance.redissonClient.getMap(MAIL_MESSAGE);
        messages.put(uuid, gson.toJson(message));
    }

    private Runnable createRunnable() {
        return new Runnable() {
            @Override
            public void run() {
//                log.info("进入发送短信线程");
                try {
                    // 拿不到锁不退出
                    LockUtil.getUntilHaveLock(MAIL_MESSAGE_LOCK);
                    // Redisson分布式可重入公平锁也是实现了java.util.concurrent.locks.Lock接口的一种RLock对象。
                    // 在提供了自动过期解锁功能的同时，保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
//                    lock = redissonClient.getFairLock(MAIL_MESSAGE_LOCK);
//                    lock.lock();

                    RMap<Object, Object> messages = redissonClient.getMap(MAIL_MESSAGE);
                    if (CollectionUtils.isEmpty(messages)) {
                        return;
                    }

                    Set<Map.Entry<Object, Object>> entries = messages.entrySet();
                    Iterator<Map.Entry<Object, Object>> iterator = entries.iterator();

                    while (iterator.hasNext()) {
                        Map.Entry<Object, Object> next = iterator.next();
                        Object key = next.getKey();
                        Object value = next.getValue();

                        if (key == null || value == null) {
                            iterator.remove();
                            continue;
                        }
                        String uuid = (String) key;
                        if (StringUtils.isEmpty(uuid)) {
                            iterator.remove();
                            continue;
                        }

                        String messageStr = (String) value;
                        if (StringUtils.isEmpty(messageStr)) {
                            iterator.remove();
                            continue;
                        }
                        SimpleMailMessage message = gson.fromJson(messageStr, SimpleMailMessage.class);
                        String[] to = message.getTo();
                        if (to == null || to.length < 1) {
                            iterator.remove();
                            continue;
                        }
                        StringBuffer toS = new StringBuffer();
                        for (String item : to) {
                            toS.append(item).append(", ");
                        }

                        try {
                            log.info("Send Mail to: " + toS.toString().substring(0, toS.toString().lastIndexOf(",")));
                            mailSender.send(message);
                        } catch (Exception e) {
                            log.error("sendEMail catch Exception {}", e);
                        }
                        iterator.remove();
                    }

                } catch (Exception e) {
                    log.error("sendEMail catch Exception {}", e);
                } finally {
                    LockUtil.unLock(MAIL_MESSAGE_LOCK);
                }
            }
//                    messages.forEach((key, value) -> {});
//                    messages.delete();
        };
    }
}
