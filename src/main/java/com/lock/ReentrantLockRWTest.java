package com.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>读写锁的使用</p>
 *
 * @Author Ellison Pei
 * @Date 2020/9/16 10:59
 **/
@Slf4j(topic = "ellison")
public class ReentrantLockRWTest {
    private static ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantLock.writeLock();

    public static void read() {
        readLock.lock();
        try {
            log.debug(Thread.currentThread().getName() + "获取读锁，开始执行");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            log.debug(Thread.currentThread().getName() + "释放读锁");
        }
    }

    public static void write() {
        writeLock.lock();
        try {
            log.debug(Thread.currentThread().getName() + "获取写锁，开始执行");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
            log.debug(Thread.currentThread().getName() + "释放写锁");
        }
    }

    public static void main(String[] args) {
        new Thread(() -> read(), "t1").start();
        new Thread(() -> read(), "t2").start();
        new Thread(() -> write(), "t3").start();
        new Thread(() -> write(), "t4").start();
    }
}
