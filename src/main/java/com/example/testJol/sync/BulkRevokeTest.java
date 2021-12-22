package com.example.testJol.sync;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * 测试：批量撤销
 *
 * @author Ellison_Pei
 * @date 2021/12/22 14:31
 * @since 1.0
 **/
@Slf4j
public class BulkRevokeTest {
    public static void main(String[] args) throws InterruptedException {
        //延时产生可偏向对象
        Thread.sleep(5000);
        // 创建一个list，来存放锁对象
        List<Object> list = new ArrayList<>();

        // 线程1
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                // 新建锁对象
                Object lock = new Object();
                synchronized (lock) {
                    list.add(lock);
                }
            }
            try {
                //为了防止JVM线程复用，在创建完对象后，保持线程thead1状态为存活
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thead1").start();

        //睡眠3s钟保证线程thead1创建对象完成
        Thread.sleep(3000);
        log.debug("打印thead1，list中第20个对象的对象头：");
        log.debug((ClassLayout.parseInstance(list.get(19)).toPrintable()));

        // 线程2
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                Object obj = list.get(i);
                synchronized (obj) {
                    if (i >= 15 && i <= 21 || i >= 38) {
                        log.debug("thread2-第" + (i + 1) + "次加锁执行中\t" +
                                ClassLayout.parseInstance(obj).toPrintable());
                    }
                }
                if (i == 17 || i == 19) {
                    log.debug("thread2-第" + (i + 1) + "次释放锁\t" +
                            ClassLayout.parseInstance(obj).toPrintable());
                }
            }
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thead2").start();


        Thread.sleep(3000);

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                Object lock = list.get(i);
                if (i >= 16 && i <= 21 || i >= 35 && i <= 41) {
                    log.debug("thread3-第" + (i + 1) + "次准备加锁\t" +
                            ClassLayout.parseInstance(lock).toPrintable());
                }
                synchronized (lock) {
                    if (i >= 16 && i <= 21 || i >= 35 && i <= 41) {
                        log.debug("thread3-第" + (i + 1) + "次加锁执行中\t" +
                                ClassLayout.parseInstance(lock).toPrintable());
                    }
                }
            }
        }, "thread3").start();

        Thread.sleep(3000);
        log.debug("查看新创建的对象");
        log.debug((ClassLayout.parseInstance(new Object()).toPrintable()));

        LockSupport.park();
    }

    // 总结
    // 批量重偏向和批量撤销是针对类的优化，和对象无关。
    // 偏向锁重偏向一次之后不可再次重偏向。
    // 当某个类已经触发批量撤销机制后，JVM会默认当前类产生了严重的问题，剥夺了该类的新实例对象使用偏向锁的权利
}
