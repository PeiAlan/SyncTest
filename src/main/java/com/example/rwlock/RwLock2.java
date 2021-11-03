package com.example.rwlock;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 关于读读并发需要注意的
 * 比如先有一个t1写锁拿到锁，后面有一些其他锁或许是读或许是写在park；
 * 当t1释放锁之后会按照FIFO的原则唤醒等待的线程；
 * 如果第一个被唤醒的是t2写锁则无可厚非；
 * 不会再跟着唤醒t3，只有等t2执行完成之后才会去唤醒T3；
 * 假设被唤醒的t3是读锁，那么t3会去判断他的下一个t4是不是读锁如果是则把t4唤醒；
 * t4唤醒之后会判断t5是不是读锁；
 * 如果t5也是则唤醒t5；依次类推；
 * 但是假设t6是写锁则不会唤醒t6了；
 * 即使后面的t7是读锁也不会唤醒t7；
 * 下面这个代码说明了这个现象
 *
 * @Author Ellison Pei
 * @Date 2020/9/19 16:23
 **/
@Slf4j
public class RwLock2 {
    //读写锁
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();

    public static void main(String[] args) throws InterruptedException {
        /**
         * t1 最先拿到写（W）锁 然后睡眠了5s
         * 之后才会叫醒别人
         */
        Thread t1 = new Thread(() -> {
            w.lock();
            try {
                log.debug("t1 +");
                TimeUnit.SECONDS.sleep(5);
                log.debug("5s 之后");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                w.unlock();
            }
        }, "t1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);/**
         * t1在睡眠的过程中 t2不能拿到 读写互斥
         * t2 一直阻塞
         */
        Thread t2 = new Thread(() -> {
            try {
                r.lock();
                log.debug("t2----+锁-------");
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("t2-----解锁-------");
                r.unlock();
            }
        }, "t2");
        t2.start();
        TimeUnit.SECONDS.sleep(1);
/**
 * t1在睡眠的过程中 t3不能拿到 读写互斥
 * t3 一直阻塞
 *
 * 当t1释放锁之后 t3和t2 能同时拿到锁
 * 读读并发
 */
        Thread t3 = new Thread(() -> {
            try {
                r.lock();
                log.debug("t3----+锁-------");
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("t3----释放-------");
                r.unlock();
            }
        }, "t3");
        t3.start();
/**
 * 拿写锁
 * t1睡眠的时候 t4也页阻塞
 * 顺序应该 t2 t3 t4
 */
        Thread t4 = new Thread(() -> {
            try {
                //读写锁 写锁上锁流程
                w.lock();
                log.debug("t4--------+---");
                TimeUnit.SECONDS.sleep(10);
                log.debug("t4--------醒来---");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("t4--------解锁---");
                w.unlock();
            }
        }, "t4");
        t4.start();
/**
 *
 * t5 是读锁
 * 他会不会和t2 t3 一起执行
 */
        Thread t5 = new Thread(() -> {
            try {
                r.lock();
                log.debug("t5--------+锁---");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log.debug("t5--------解锁---");
                r.unlock();
            }
        }, "t5");
        t5.start();
    }
}

