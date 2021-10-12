package com.shadow.demo7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

//这里是重入锁的另外一种情况，继承
@Slf4j(topic = "enjoy")
public class Demo {

    synchronized void test(){
        log.debug("demo testJol start........");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("demo testJol end........");
    }

    public static void main(String[] args) {
            new Demo2().test();
    }

}
@Slf4j(topic = "enjoy")
class Demo2 extends Demo {

    @Override
    synchronized void test(){
        log.debug("demo2 testJol start........");
        super.test();
        log.debug("demo2 testJol end........");
    }

}