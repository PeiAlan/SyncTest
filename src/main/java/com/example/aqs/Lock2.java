package com.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j(topic = "enjoy")
public class Lock2 {
    //读写锁
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();



    public static void main(String[] args) throws InterruptedException {

        /**
         * t1  最先拿到写（W）锁 然后睡眠了5s
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

        TimeUnit.SECONDS.sleep(1);


        /**
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
         * 顺序应该 t2 t3  t4
         */

        Thread t4 = new Thread(() -> {
            try {
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
