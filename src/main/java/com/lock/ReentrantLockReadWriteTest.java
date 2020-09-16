package com.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>ReentrantReadWriteLock读写锁插队策略测试</p>
 * 策略1:
 *      如果允许读插队，就是说线程5读先于线程3写操作执行，因为读锁是共享锁，不影响后面的线程3的写操作
 *      这种策略可以提高一定的效率，却可能导致像线程3这样的线程一直在等待中
 *      因为可能线程5读操作之后又来了n个线程也进行读操作
 *      造成  线程饥饿.
 *
 * 策略2:
 *      是不允许插队，即线程5的读操作必须排在线程3的写操作之后，放入队列中，排在线程3之后
 *      这样能  避免线程饥饿。
 *
 * @Author Ellison Pei
 * @Date 2020/9/16 11:26
 **/
@Slf4j(topic = "ellison")
public class ReentrantLockReadWriteTest {
    private static ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantLock.writeLock();

    public static void read() {
        log.debug(Thread.currentThread().getName() + "开始尝试获取读锁");
        readLock.lock();
        try {
            log.debug(Thread.currentThread().getName() + "========获取到读锁，开始执行");
            Thread.sleep(20);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            log.debug(Thread.currentThread().getName() + "释放读锁");
        }
    }

    public static void write() {
        log.debug(Thread.currentThread().getName() + "开始尝试获取写锁");
        writeLock.lock();
        try {
            log.debug(Thread.currentThread().getName() + "========获取到写锁，开始执行");
            Thread.sleep(40);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.debug(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> write(), "t1").start();
        new Thread(() -> read(), "t2").start();
        new Thread(() -> read(), "t3").start();
        new Thread(() -> write(), "t4").start();
        new Thread(() -> read(), "t5").start();
        new Thread(() -> {
            Thread[] threads = new Thread[1000];
            for (int i = 0; i < 1000; i++) {
                threads[i] = new Thread(() -> read(), "子线程创建的Thread" + i);
            }
            for (int i = 0; i < 1000; i++) {
                threads[i].start();
            }
        }).start();
    }
}
