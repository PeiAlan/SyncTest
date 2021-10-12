package com.test;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * <p>TODO</p>
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
