package com.example.testJol.sync;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * 轻量级锁 测试与跟踪
 * 倘若偏向锁失败，虚拟机并不会立即升级为重量级锁，它还会尝试使用一种称为轻量级锁的优化手段，此时Mark Word 的结构也变为轻量级锁的结构。
 * 轻量级锁所适应的场景是线程交替执行同步块的场合，如果存在同一时间多个线程访问同一把锁的场合，就会导致轻量级锁膨胀为重量级锁。
 *
 * @author Ellison_Pei
 * @date 2021/12/21 17:35
 * @since 1.0
 **/
@Slf4j
public class LightWeightLockTest {

    @Test
    public void testLockEscalation() throws InterruptedException {
        log.debug(ClassLayout.parseInstance(new Object()).toPrintable());
        //HotSpot 虚拟机在启动后有个 4s 的延迟才会对每个新建的对象开启偏向锁模式
        Thread.sleep(4000);
        Object obj = new Object();
        // 思考： 如果对象调用了hashCode,还会开启偏向锁模式吗
        obj.hashCode();
        //log.debug(ClassLayout.parseInstance(obj).toPrintable());

        new Thread(() -> {
            log.debug(Thread.currentThread().getName() + "开始执行。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());
            synchronized (obj) {
                log.debug(Thread.currentThread().getName() + "获取锁执行中。。。\n"
                        + ClassLayout.parseInstance(obj).toPrintable());
            }
            log.debug(Thread.currentThread().getName() + "释放锁。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());
        }, "thread1").start();

        Thread.sleep(5000);
        log.debug(ClassLayout.parseInstance(obj).toPrintable());
    }

    /**
     * 测试：锁升级场景
     * 偏向锁升级轻量级锁
     * 模拟两个线程轻微竞争场景
     *
     * @throws InterruptedException
     */
    @Test
    public void testLockEscalation1() throws InterruptedException {
        log.debug(ClassLayout.parseInstance(new Object()).toPrintable());
        //HotSpot 虚拟机在启动后有个 4s 的延迟才会对每个新建的对象开启偏向锁模式
        Thread.sleep(4000);
        Object obj = new Object();

        Thread thread1 = new Thread(() -> {
            log.debug(Thread.currentThread().getName() + "开始执行。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());
            synchronized (obj) {
                // 思考：偏向锁执行过程中，调用hashcode会发生什么？
                //obj.hashCode();
                log.debug(Thread.currentThread().getName() + "获取锁执行中。。。\n"
                        + ClassLayout.parseInstance(obj).toPrintable());
            }
            log.debug(Thread.currentThread().getName() + "释放锁。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());
        }, "thread1");
        thread1.start();

        //控制线程竞争时机， 控制了就是轻量锁
        // 不控制 就是多线程竞争场景了，直接升级重量锁
        Thread.sleep(1);

        Thread thread2 = new Thread(() -> {
            log.debug(Thread.currentThread().getName() + "开始执行。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());
            synchronized (obj) {
                log.debug(Thread.currentThread().getName() + "获取锁执行中。。。\n"
                        + ClassLayout.parseInstance(obj).toPrintable());
            }
            log.debug(Thread.currentThread().getName() + "释放锁。。。\n"
                    + ClassLayout.parseInstance(obj).toPrintable());
        }, "thread2");
        thread2.start();

        Thread.sleep(5000);
        log.debug(ClassLayout.parseInstance(obj).toPrintable());
    }
    // 思考：重量级锁释放之后变为无锁，此时有新的线程来调用同步块，会获取什么锁？
    // 新的线程获取 轻量锁


}
