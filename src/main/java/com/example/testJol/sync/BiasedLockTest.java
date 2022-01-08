package com.example.testJol.sync;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * 偏向锁延迟偏向
 * <p>
 * //关闭延迟开启偏向锁
 * -XX:BiasedLockingStartupDelay=0
 * //禁止偏向锁
 * -XX:-UseBiasedLocking
 * //启用偏向锁
 * -XX:+UseBiasedLocking
 *
 * @author Ellison_Pei
 * @date 2021/12/21 11:08
 * @since 1.0
 **/
@Slf4j
public class BiasedLockTest {

    /**
     * -XX:+PrintFlagsFinal
     * 打印 JVM 所有默认值
     */
    @Test
    public void printGcDetail() {
        System.out.println(111);
    }

    /**
     * 前提是  没有关闭偏向锁延迟
     * 偏向锁默认开启， 刚启动为无锁状态， 4s后为偏向锁状态
     * <p>
     * 4s后偏向锁为可偏向或者匿名偏向状态：
     */
    @Test
    public void test1() throws InterruptedException {
        log.debug(ClassLayout.parseInstance(new Object()).toPrintable());
        Thread.sleep(4000);
        // 4s后偏向锁为可偏向或者匿名偏向状态
        log.debug(ClassLayout.parseInstance(new Object()).toPrintable());
    }

    /**
     * 如果锁LockEscalationDemo.class会是什么状态？
     */
    @Test
    public void test2() throws InterruptedException {
        // 001
        log.debug(ClassLayout.parseInstance(new Object()).toPrintable());
        Thread.sleep(4000);
        // 101
        // new Object()  101  00000000  匿名偏向（偏向锁可偏向，但是没有偏向）
        //Object o = new Object();
        //log.debug(ClassLayout.parseInstance(o).toPrintable());
        synchronized (BiasedLockTest.class) {
            // 4s后偏向锁为可偏向或者匿名偏向状态 101
            log.debug(ClassLayout.parseInstance(new Object()).toPrintable());
        }
    }

    /**
     * 偏向锁状态跟踪
     */
    @Test
    public void testLockEscalation() throws InterruptedException {
        // 001
        log.debug(ClassLayout.parseInstance(new Object()).toPrintable());
        //HotSpot 虚拟机在启动后有个 4s 的延迟才会对每个新建的对象开启偏向锁模式
        Thread.sleep(4000);
        // 101  000000000000000
        Object obj = new Object();
        // 这种情况就没有 偏向锁了，后面thread直接 吧 obj 对象加成轻量锁，因为此时偏向锁被撤销了
        //obj.hashCode();
        //log.debug(ClassLayout.parseInstance(obj).toPrintable());

        new Thread(() -> {
            log.debug(Thread.currentThread().getName() + "开始执行。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());
            obj.hashCode();
            log.debug(Thread.currentThread().getName() + "开始执行后调用了hashCode()。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());
            synchronized (obj) {
                log.debug(Thread.currentThread().getName() + "获取锁执行中。。。\n"
                        + ClassLayout.parseInstance(obj).toPrintable());
            }
            log.debug(Thread.currentThread().getName() + "释放锁。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());

            //method();
        }, "thread1").start();

        Thread.sleep(5000);
        log.debug(ClassLayout.parseInstance(obj).toPrintable());
    }


    /**
     * 偏向锁撤销之调用wait/notify
     */
    @Test
    public void biasedRevokeTest() throws InterruptedException {
        log.debug(ClassLayout.parseInstance(new Object()).toPrintable());
        //HotSpot 虚拟机在启动后有个 4s 的延迟才会对每个新建的对象开启偏向锁模式
        Thread.sleep(4000);
        Object obj = new Object();
        log.debug(ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj) {
            log.debug(Thread.currentThread().getName() + "拿到锁。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());
            // 思考：偏向锁执行过程中，调用hashcode会发生什么？   重量锁
            //obj.hashCode();
            // 偏向锁状态执行obj.notify() 会升级为轻量级锁
            //obj.notify();
            try {
                // 调用obj.wait(timeout) 会升级为重量级锁
                obj.wait(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.debug(Thread.currentThread().getName() + "获取锁执行中。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());
        }
    }

}
