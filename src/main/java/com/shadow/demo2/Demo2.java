package com.shadow.demo2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 不要以字符串常量作为锁定的对象
 *
 */
@Slf4j(topic = "enjoy")
public class Demo2 {

    String s1 = "hello";

    String s2 = "hello";

    public void test1(){
        synchronized (s1) {
           log.debug("t1 start...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t1 end...");
        }
    }

    public void test2(){
        synchronized (s2) {
            log.debug("t2 start...");
        }
    }

    public static void main(String[] args) {
        Demo2 demo = new Demo2();
        //啓動t1
        new Thread(demo :: test1,"t1").start();
        //启动t2
        new Thread(demo :: test2,"t2").start();
    }

}
