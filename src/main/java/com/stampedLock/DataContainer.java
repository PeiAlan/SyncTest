package com.stampedLock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * 一个数据容器
 * 不支持重入
 * 不支持条件
 */
@Slf4j(topic = "enjoy")
public class DataContainer {
    int i;
    long stampw=0l;

    public void setI(int i) {
        this.i = i;
    }

    private final StampedLock lock = new StampedLock();

    //首先 加 StampedLock
    @SneakyThrows
    public int read() {
        //尝试一次乐观读
        long stamp = lock.tryOptimisticRead();

        log.debug("StampedLock 读锁拿到的戳{}", stamp);
        //1s之后验戳
        //TimeUnit.SECONDS.sleep(1);
        //验戳
        if (lock.validate(stamp)) {//线程安全问题
            log.debug("StampedLock 验证完毕stamp{}, data.i:{}", stamp, i);
            TimeUnit.SECONDS.sleep(10);
            return i;
        }
        //一定验证失败
        log.debug("验证失败 被写线程给改变了{}", stampw);
        try {
            //锁的升级 也会改戳
            stamp = lock.readLock();
            log.debug("升级之后的加锁成功 {}", stamp);
            TimeUnit.SECONDS.sleep(1);
            log.debug("升级读锁完毕{}, data.i:{}", stamp, i);
            return i;
        } finally {
            log.debug("升级锁解锁 {}", stamp);
            lock.unlockRead(stamp);
        }
    }



    @SneakyThrows
    public void write(int i) {
        //cas 加鎖
        stampw = lock.writeLock();
        log.debug("写锁加锁成功 {}", stampw);
        try {
            TimeUnit.SECONDS.sleep(5);
            this.i = i;
        } finally {
            log.debug("写锁解锁 {},data.i{}", stampw,i);
            lock.unlockWrite(stampw);
        }
    }
}
