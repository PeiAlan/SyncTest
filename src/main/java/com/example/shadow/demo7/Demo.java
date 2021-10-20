package com.example.shadow.demo7;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

//这里是重入锁的另外一种情况，继承
public class Demo {
    private static Logger log = LogManager.getLogger(Demo.class);

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

class Demo2 extends Demo {
    private static Logger log = LogManager.getLogger(Demo2.class);

    @Override
    synchronized void test(){
        log.debug("demo2 testJol start........");
        super.test();
        log.debug("demo2 testJol end........");
    }

}