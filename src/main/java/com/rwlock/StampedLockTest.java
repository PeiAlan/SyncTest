package com.rwlock;

import ch.qos.logback.core.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

/**
 * <p>TODO</p>
 *
 * @Author Ellison Pei
 * @Date 2020/9/19 17:34
 **/
@Slf4j(topic = "ellison")
public class StampedLockTest {
    static StampedLock stampedLock = new StampedLock();
    static Lock readLock = stampedLock.asReadLock();
    static Lock writeLock = stampedLock.asWriteLock();

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            writeLock.lock();
            try {
                log.debug("writeLock ---- t1 加锁成功！");
                log.debug("t1  开始睡眠");
                TimeUnit.SECONDS.sleep(5);
                log.debug("t1  睡眠了5S  醒来");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            readLock.lock();
            try {
                log.debug("readLock---t2 加锁成功！");
                TimeUnit.SECONDS.sleep(5);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                log.debug("t2 释放锁");
            }
        }, "t2").start();

        new Thread(()->{
            readLock.lock();
            try {
                log.debug("readLock---t3 加锁成功！");

            } finally {
                readLock.unlock();
                log.debug("t3 释放锁");
            }
        }, "t3").start();

    }
}
