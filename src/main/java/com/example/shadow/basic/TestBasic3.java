package com.example.shadow.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * 永远y之前
 * 锁的是不同的对象
 *
 */
@Slf4j(topic = "ellison")
public class TestBasic3 {
    /**
     * 执行结果永远是y在前
     * 因为 锁的是不同的对象，x睡眠了1秒，所以一定是y先打印
     * @param args
     */
    public static void main(String[] args) {
        BasicLock basicLock = new BasicLock();
        BasicLock basicLock1 = new BasicLock();
        new Thread(()->{
            log.debug("start");
            basicLock.x();
        },"t1").start();

        new Thread(()->{
            log.debug("start");
            basicLock1.y();
        },"t2").start();
    }
}
