package com.shadow.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * 1、等1s  打印x  打印y
 * 2、先打印y  等1s  x
 */
@Slf4j(topic = "enjoy")
public class TestBasic {
    public static void main(String[] args) {
        BasicLock basicLock = new BasicLock();


        new Thread(()->{
            log.debug("start");
            basicLock.x();
        },"t1").start();

        new Thread(()->{
            log.debug("start");
            basicLock.y();
        },"t2").start();
    }
}
