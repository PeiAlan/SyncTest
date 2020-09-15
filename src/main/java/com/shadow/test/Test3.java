package com.shadow.test;

import lombok.extern.slf4j.Slf4j;


/**
 * 1、什么是对象头？
 *  什么是对象？内存级别而言来研究什么是对象
 */

@Slf4j(topic = "enjoy")
public class Test3 {
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
        //jack
        new Thread(() -> {
            synchronized (key) {
                log.debug("有没有女人[{}]", isPrettyGril);
                if (!isPrettyGril) {
                    log.debug("没有女人！等女人");
                    try {
                        key.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("老板把我叫醒了 有没有女人？[{}]", isPrettyGril);
                if (isPrettyGril) {
                    log.debug("------男女搭配干活不累；啪啪啪写完了代码");
                }else{
                    log.debug("------下班回家----");
                }
            }
        }, "jack").start();




        new Thread(() -> {
            synchronized (key) {
                log.debug("有没有钱[{}]", isMoney);
                if (!isMoney) {
                    log.debug("没有钱则等钱 wait");
                    try {
                        key.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("老板把我叫醒了 有没有？[{}]", isMoney);
                if (isMoney) {
                    log.debug("------卧槽---有钱了干活了");
                }else{
                    log.debug("------没钱回家----");
                }
            }
        }, "rose").start();




//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                    synchronized (key) {
//                        log.debug("我们10个屌丝工作了");
//                    }
//                }, "其它人").start();
//        }


        Thread.sleep(1000);
        new Thread(() -> {
            synchronized (key) {
                isMoney = true;
                log.debug("给钱了");
                //随机叫醒一个
                key.notify();
            }
        }, "boss").start();

    }




}
