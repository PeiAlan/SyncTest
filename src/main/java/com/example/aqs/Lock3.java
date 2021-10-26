package com.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "enjoy")
public class Lock3 {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
//            lock.lock();
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.debug("t1  不获取锁了------");
                e.printStackTrace();
            }
            try {
                log.debug("t1------");
            } finally {
                //如果重入的次数和unlock的次数不同则不能完全释放锁
                lock.unlock();
            }
        }, "t1");
        t1.start();

        lock.lock();
        try {
            log.debug("main------");
            t1.interrupt();
            TimeUnit.SECONDS.sleep(100000);
        } finally {
            lock.unlock();
        }
    }


    /**
     * 读写锁为什么不能升级？
     *
     * readWriteLock.r//  t1   t2  t3  t4
     *    td//t1 5
     *      readWriteLock.w  // 拿不到  t1等 t2 和t3 释放  t2 等t1 和t3
     *       td//t2
     *     readWriteLock.uw
     *     readWriteLock.rw
     *
     * 读写锁为什么可以降级?
     *
     *
     *
     */
}
