package com.open.custom.api.utils;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class test {

    private static final Logger log = LoggerFactory.getLogger(test.class);

    static int inventory = 20;
    static int NUM = 20;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        Config config = new Config();
        config.useSingleServer().setAddress("redis://139.186.66.121:6000");
        config.useSingleServer().setPassword("1qaz!QAZ2wsx@WSX");
        final RedissonClient client = Redisson.create(config);


//
//        ThreadPoolExecutor threadPoolExecutor =
//                new ThreadPoolExecutor(inventory, inventory, 10L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.error("开始加锁");
                RLock lock = client.getLock("lock1");
                lock.lock();
                log.error("加锁成功-执行业务逻辑");
                try {
                    inventory--;
                    log.error("当前库存数:" + inventory);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.error("执行业务逻辑完成");
//                lock.unlock();
//                log.error("解锁完成");
            }
        }, 0, 5, TimeUnit.SECONDS);



        ScheduledExecutorService scheduledExecutor1 = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor1.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.error("开始加锁1");
                RLock lock = client.getLock("lock1");
                lock.lock();
                log.error("加锁成功-执行业务逻辑1");
                try {
                    inventory--;
                    log.error("当前库存数1:" + inventory);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.error("执行业务逻辑完成1");
//                lock.unlock();
//                log.error("解锁完成");
            }
        }, 0, 5, TimeUnit.SECONDS);


//        for (int i = 0; i <= NUM; i++) {
//            threadPoolExecutor.execute(new Runnable() {
//                public void run() {
//                    lock.lock();
//                    inventory--;
//                    System.out.println(inventory);
//                    lock.unlock();
//                }
//            });
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("执行线程数:" + NUM + "   总耗时:" + (end - start) + "  库存数为:" + inventory);
    }

    class MyCallable implements Callable {
        private String oid;

        MyCallable(String oid) {
            this.oid = oid;
        }

        public Object call() throws Exception {
            Thread.currentThread().sleep(5000);

            System.out.println(oid + "任务返回的内容");
            return oid + "任务返回的内容";
        }
    }
}
