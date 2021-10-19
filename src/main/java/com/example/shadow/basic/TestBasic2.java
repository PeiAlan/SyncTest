package com.example.shadow.basic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 输出结果
 */
public class TestBasic2 {
    private static Logger log = LogManager.getLogger(TestBasic2.class);
    public static void main(String[] args) {
        BasicLock basicLock = new BasicLock();
        new Thread(()->{
            log.debug("x---start");
            basicLock.x();
        },"x").start();
        new Thread(()->{
            log.debug("y---start");
            basicLock.y();
        },"y").start();
        new Thread(()->{
            log.debug("z---start");
            basicLock.z();
        },"z").start();
    }
}
