package com.shadow.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * 输出结果
 */
@Slf4j(topic = "enjoy")
public class TestBasic2 {
    public static void main(String[] args) {
        BasicLock basicLock = new BasicLock();
        new Thread(()->{
            log.debug("x---start");
            basicLock.x();
        },"t1").start();
        new Thread(()->{
            log.debug("y---start");
            basicLock.y();
        },"t2").start();
        new Thread(()->{
            log.debug("z---start");
            basicLock.z();
        },"t3").start();
    }
}
