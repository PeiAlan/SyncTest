package com.rwlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>读锁与血锁 加锁过程实例代码</p>
 *
 * @Author Ellison Pei
 * @Date 2020/9/19 15:48
 **/
@Slf4j(topic = "ellison")
public class RwTest {

    //读写锁
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            w.lock();
            try {
                log.debug("t1 w---加锁成功");
            } finally {
                w.unlock();
            }
        }, "t1");
        t1.start();
    }
}
