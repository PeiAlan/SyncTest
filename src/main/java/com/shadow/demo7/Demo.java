package com.shadow.demo7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

//这里是重入锁的另外一种情况，继承
@Slf4j(topic = "enjoy")
public class Demo {

    synchronized void test(){
        log.debug("demo test start........");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("demo test end........");
    }

    public static void main(String[] args) {
            new Demo2().test();
    }

}
@Slf4j(topic = "enjoy")
class Demo2 extends Demo {

    @Override
    synchronized void test(){
        log.debug("demo2 test start........");
        super.test();
        log.debug("demo2 test end........");
    }

}