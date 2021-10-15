package com.lock;

import com.shadow.test.L;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * Synchronized 原理
 * //关闭延迟开启偏向锁  -XX:BiasedLockingStartupDelay=0
 * //禁止偏向锁          -XX:-UseBiasedLocking
 * //启用偏向锁          -XX:+UseBiasedLocking
 * <p>
 * -XX:+PrintFlagsInitial  打印所有JVM 所有XX参数的默认值
 *
 * @author Ellison_Pei
 * @date 2021/10/13 09:58
 * @since 1.0
 **/
@Slf4j
public class SyncTest {

    static final L LOCK = new L();
    static Thread t1, t2, t3;

    public static void main(String[] args) throws InterruptedException {
        // 101 偏向锁可偏向未偏向   也叫 匿名偏向
        log.debug(ClassLayout.parseInstance(LOCK).toPrintable());
        // 001 调用了hashCode  无锁
        log.debug(Integer.toHexString(LOCK.hashCode()));
        log.debug(ClassLayout.parseInstance(LOCK).toPrintable());
        t1 = new Thread(() -> {
            synchronized (LOCK) {
                // 1、101  如果注释掉调用hashCode的代码，没有调用hashcode方法那么此处就是偏向锁，前56位存的当前线程ID
                // 2、000  轻量锁  ==== 如果调用了hashCode，那么此处就是轻量锁
                log.debug(ClassLayout.parseInstance(LOCK).toPrintable());
            }
        });
        t1.setName("t1");
        t1.start();
        t1.join();

    }
}
