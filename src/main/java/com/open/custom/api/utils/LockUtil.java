package com.open.custom.api.utils;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LockUtil {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 强制解锁时间设置
     */
    private long LOCK_TIME = 2L;

    /**
     * 等待时间
     **/
    private long WAIT_TIME = 3L;

    /**
     * 休眠时间
     **/
    private long SLEEP_TIME = 1000L;


    /**
     * 根据key值获取锁
     *
     * @param lockName
     */
    public void lock(String lockName) {
        RLock lock = redissonClient.getLock(lockName);
        //lock提供带timeout参数，timeout结束强制解锁，防止死锁
//        lock.lock(LOCK_TIME, TimeUnit.SECONDS);
        lock.lock();
    }

    /**
     * 获取锁
     *
     * @param lockName
     * @return
     * @throws InterruptedException
     */
    public boolean tryLock(String lockName) throws InterruptedException {
        RLock lock = redissonClient.getLock(lockName);
        //tryLock，第一个参数是等待时间。 第二个参数 强制锁释放时间
//        return lock.tryLock(WAIT_TIME, LOCK_TIME, TimeUnit.SECONDS);
        return lock.tryLock();
    }

    /**
     * 解锁
     *
     * @param lockName
     */
    public void unLock(String lockName) {
        RLock lock = redissonClient.getLock(lockName);
        lock.unlock();
    }

    /**
     * 获取锁，一直等待到取到锁后返回
     *
     * @param lockName
     * @throws InterruptedException
     */
    public boolean getUntilHaveLock(String lockName) throws InterruptedException {
        while (true) {
            if (tryLock(lockName)) {
                return true;
            } else {
                Thread.sleep(SLEEP_TIME);
            }
        }
    }
}
