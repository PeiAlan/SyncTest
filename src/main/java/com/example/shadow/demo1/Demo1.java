package com.example.shadow.demo1;

import lombok.extern.slf4j.Slf4j;

/**
 * synchronized关键字
 * synchronized关键字锁定的是对象不是代码块,demo中锁的是object对象的实例
 * 锁定的对象有两种:1.类的实例 2.类对象(类锁)
 * 加synchronized关键字之后不一定能实现线程安全，具体还要看锁定的对象是否唯一。
 */
@Slf4j
public class Demo1 {
    private int count = 10;
    private Object object = new Object();
    public void test(){
        synchronized (object){
            count--;
            log.debug(Thread.currentThread().getName() + " count = " + count);
        }
    }
    /**
     * synchronized关键字
     * synchronized关键字锁定的是对象不是代码块,demo中锁的是object对象的实例
     * 锁定的对象有两种:1.类的实例 2.类对象(类锁)
     * 加synchronized关键字之后不一定能实现线程安全，具体还要看锁定的对象是否唯一。
     */
    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        new Thread(demo1::test, "t1").start();

        demo1.object = new Object();
        new Thread(demo1::test, "t2").start();
    }
}
