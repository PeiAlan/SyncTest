package com.example.shadow.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * 此时 y永久在x之前执行
 * x线程锁的是 当前 BasicLock1 类
 * y线程锁的是  实例对象 new BasicLock1()
 */
@Slf4j
public class TestBasic4 {
    /**
     * t1、t2启动时 ，线程打印是存在竞争关系的
     * 然而在在执行 x  y  方法时，x  sleep了1秒，所有时y先行打印
     * 锁的对象不一样
     * @param args
     */
    public static void main(String[] args) {
        BasicLock1 basicLock1 = new BasicLock1();
        // x 锁的是BasicLock1 这个类的class
        new Thread(()->{
            log.debug("t1--start");
            basicLock1.x();
        },"t1").start();

        /**
         * y 锁的是new BasicLock1() 对象
         */
        new Thread(()->{
            log.debug("t2--start");
            basicLock1.y();
        },"t2").start();
    }
}
