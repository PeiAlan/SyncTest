package com.example.lockTest;

import org.openjdk.jol.info.ClassLayout;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Ellison Pei
 * 测试 偏向锁，轻量级锁，重量级锁标记变化
 * 关闭延迟开启偏向锁： -XX:BiasedLockingStartupDelay=0
 * 无锁 001
 * 偏向锁 101
 * 轻量级锁 00
 * 重量级锁 10
 */
@Slf4j
public class LockEscalationDemo{

    public static void main(String[] args) throws InterruptedException {

        log.debug(ClassLayout.parseInstance(new Object()).toPrintable());
        //HotSpot 虚拟机在启动后有个 4s 的延迟才会对每个新建的对象开启偏向锁模式
        Thread.sleep(5000);
        Object obj = new Object();
        // 思考： 如果对象调用了hashCode,还会开启偏向锁模式吗
        //obj.hashCode();
        log.debug(ClassLayout.parseInstance(obj).toPrintable());

        new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug(Thread.currentThread().getName()+"开始执行。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
                synchronized (obj){
                    // 思考：偏向锁执行过程中，调用hashcode会发生什么？
                    //obj.hashCode();
//                    //obj.notify();
////                    try {
////                        obj.wait(50);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }

                    log.debug(Thread.currentThread().getName()+"获取锁执行中。。。\n"
                            +ClassLayout.parseInstance(obj).toPrintable());
                }
                log.debug(Thread.currentThread().getName()+"释放锁。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
            }
        },"thread1").start();

        //控制线程竞争时机
        Thread.sleep(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug(Thread.currentThread().getName()+"开始执行。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
                synchronized (obj){

                    log.debug(Thread.currentThread().getName()+"获取锁执行中。。。\n"
                            +ClassLayout.parseInstance(obj).toPrintable());
                }
                log.debug(Thread.currentThread().getName()+"释放锁。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
            }
        },"thread2").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug(Thread.currentThread().getName()+"开始执行。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
                synchronized (obj){

                    log.debug(Thread.currentThread().getName()+"获取锁执行中。。。\n"
                            +ClassLayout.parseInstance(obj).toPrintable());
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug(Thread.currentThread().getName()+"释放锁。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
            }
        },"thread3").start();


        Thread.sleep(5000);
        log.error("" + "T1，T2，T3都是放锁后的MarkWord：");
        log.debug(ClassLayout.parseInstance(obj).toPrintable());

//        // 下面打印还是 无锁001，说明，jvm在锁降级后不会在把对象头中`markWord`置为偏向状态
//        Thread.sleep(5000);
//        log.error("" + "T1，T2，T3都是放锁后的MarkWord，沉睡5秒后：");
//        log.debug(ClassLayout.parseInstance(obj).toPrintable());


        log.error("" + "开始启用新的线程");
        new Thread(() -> {
            log.debug(Thread.currentThread().getName()+"开始执行。。。\n"
                    +ClassLayout.parseInstance(obj).toPrintable());
            synchronized (obj){
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug(Thread.currentThread().getName()+"获取锁执行中。。。\n"
                        +ClassLayout.parseInstance(obj).toPrintable());
            }
            log.debug(Thread.currentThread().getName()+"释放锁。。。\n"
                    +ClassLayout.parseInstance(obj).toPrintable());
        }, "Thread4").start();

    }
}