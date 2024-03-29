package com.example.testJol;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 *
 *
 * 问题： synchronized是偏向锁还是轻量锁还是重量锁？
 * 答案：  有时候是，有时候不是。
 *          如果同一个线程加锁，就是偏向锁；
 *          如果是多线程交替执行，那就是轻量锁；
 *          如果是多线程资源竞争，那就是重量锁，底层用 pthread_mutex实现；
 *
 *
 * @author Ellison_Pei
 * @date 2021/10/12 14:44
 * @since 1.0
 **/
@Slf4j
public class TestLockUpgrade {

    static Tlock l = new Tlock();
    static Thread t1;
    static Thread t2;

    public static void main(String[] args) throws InterruptedException {
        log.error("没有 hashCode");
        // 无锁 可偏向 但是没有偏向
        log.debug(ClassLayout.parseInstance(l).toPrintable());
//        log.info(Integer.toHexString(l.hashCode()));
        t1 = new Thread(() -> lock());
        t2 = new Thread(() -> lock());
        t1.setName("t1");
        t1.start();
        t1.join();  // 加了join进行排队不需要资源竞争就是轻量锁了， 不排队竞争了就是重量锁了
        t2.start();
        t2.setName("t2");
    }


    public static void lock(){
        synchronized (l){
            // 有锁，有hash就是轻量锁，无hash就是偏向锁已偏向
            log.debug(Thread.currentThread().getName());
            log.debug(ClassLayout.parseInstance(l).toPrintable());
        }
    }
}
