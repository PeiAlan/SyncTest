package com.shadow.demo8;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * synchronized 和异常的关系
 * T2线程能否执行？
 */
@Slf4j(topic = "enjoy")
public class Demo {
    Object o = new Object();

    int count = 0;

     void test(){
         synchronized(o) {
             //t1进入并且启动
             log.debug(Thread.currentThread().getName() + " start......");
             //t1 会死循环 t1 讲道理不会释放锁
             while (true) {
                 count++;
                 log.debug(Thread.currentThread().getName() + " count = " + count);
                 try {
                     TimeUnit.SECONDS.sleep(1);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 //加5次之后 发生异常
                 /**
                  * 如果程序发生异常如果没有try 则会释放锁
                  * 反之不会释放锁
                  */
                 if (count == 5) {
                     int i = 1 / 0;
                 }
             }
         }
    }

    public static void main(String[] args) {
        Demo demo11 = new Demo();
       // Runnable r = () -> demo11.test();
       // new Thread(r, "t1").start();

        new Thread(()->{
            demo11.test();
        },"t1").start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            demo11.test();
        }, "t2").start();
    }

}
