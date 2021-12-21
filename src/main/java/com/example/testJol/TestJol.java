package com.example.testJol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jol.info.ClassLayout;

/**
 *  https://idea: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@github.com/PeiAlan/springDemo.git
 *
 * @author Ellison_Pei
 * @date 2021/10/11 14:48
 * @since 1.0
 **/
public class TestJol {

    private static Logger log = LogManager.getLogger(TestJol.class);
    static Tlock l = new Tlock();
    static Thread t1;
    static Thread t2;

    /**
     * 0x 00000000 00000001
     *
     * //关闭延迟开启偏向锁
     * -XX:BiasedLockingStartupDelay=0
     * //禁止偏向锁
     * -XX:-UseBiasedLocking
     * //启用偏向锁
     * -XX:+UseBiasedLocking
     *
     *
     * 001-->无锁
     * 101-->偏向锁
     * 000-->轻量锁
     * 010-->重量锁
     * 011-->GC标记
     *
     * 批量重偏向阈值
     *    intx BiasedLockingBulkRebiasThreshold          = 20
     *    intx BiasedLockingBulkRevokeThreshold          = 40
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        log.error("没有 hashCode");
        // 无锁 可偏向 但是没有偏向
        log.debug(ClassLayout.parseInstance(l).toPrintable());

//        log.error("调用了 hashCode");
        // 无锁 可偏向 已经偏向
//        log.info(Integer.toHexString(l.hashCode()));
        log.debug(ClassLayout.parseInstance(l).toPrintable());


        /*
         * 问题： synchronized是偏向锁还是轻量锁还是重量锁？
         * 答案：  有时候是，有时候不是。
         *          如果同一个线程加锁，就是偏向锁；
         *          如果是多线程交替执行，那就是轻量锁；
         *          如果是多线程资源竞争，那就是重量锁，底层用 pthread_mutex实现；
         */
        synchronized (l){
            // 有锁，有hash就是轻量锁，无hash就是偏向锁已偏向
            log.debug(ClassLayout.parseInstance(l).toPrintable());
        }
    }


}
