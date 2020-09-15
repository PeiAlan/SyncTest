package com.shadow.test;

import lombok.extern.slf4j.Slf4j;


/**
 * 1、什么是对象头？
 *  什么是对象？内存级别而言来研究什么是对象
 */

@Slf4j(topic = "enjoy")
public class Test6 {
    static boolean isPrettyGril = false;
    static boolean isMoney = false;
    static  Object key = new Object();


    /**
     * 多线程情况下 假设某个线程拿到锁了，但是他需要满足某个条件
     * 才能执行 如果用sleep 会导致在没有满足条件的情况下；我一直阻塞
     * 一直持有锁，别的线程也拿不到锁
     * 得有办公室的key
     * boss 需要叫jack 去加班编程；还有其他十个人是自愿加班
     * jack不愿加班---你给我女人
     * @param args
     * @throws InterruptedException
     */

    public static   void main(String[] args) throws InterruptedException {
        log.debug("mian--------");
        new Thread(() -> {
            synchronized (key) {
                try {
                    /**
                     * wait可以带参数
                     * 第二个纳秒参数 是直接把毫秒+1
                     */
                    key.wait(5000,50);
                    System.out.println("5秒钟之后 mian--end------");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "boss").start();



        Thread.sleep(100);



    }




}
