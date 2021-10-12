package com.shadow.demo1;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "enjoy")
public class Demo3 {
    private int count = 10;
    //直接加在方法声明上，相当于是synchronized(this)
    public synchronized void test(){
        count--;
        log.debug(Thread.currentThread().getName() + " count = " + count);
    }
    public static void main(String[] args) {
        Demo3 demo3 = new Demo3();
        new Thread(demo3::test,"t1").start();

        //这时  线程是安全的
//        new Thread(demo3::testJol,"t2").start();

        //此时又是线程不安全的，因为锁的对象变了
        Demo3 demo31 = new Demo3();
        new Thread(demo31::test, "t3").start();
    }

}
