package com.example.testJol.sync;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * JDK 对 Synchronized 关键字的优化
 *
 * @author Ellison_Pei
 * @date 2021/12/22 14:53
 * @since 1.0
 **/
@Slf4j
public class OptimumSync {

    /**
     * 锁粗化
     * 一系列的连续操作都会对同一个对象反复加锁及解锁，甚至加锁操作是出现在循环体中的，即使没有出现线程竞争，频繁地进行互斥同步操作也会导致不必要的性能损耗。
     * 如果JVM检测到有一连串零碎的操作都是对同一对象的加锁，将会扩大加锁同步的范围（即锁粗化）到整个操作序列的外部。
     */
    @Test
    public void test1() {
        StringBuffer stringBuffer = new StringBuffer();
        // 锁粗化
        stringBuffer.append("aaa").append("bbb").append("ccc");
    }

    /**
     * 锁消除
     * <p>
     * 锁消除即删除不必要的加锁操作。
     * 锁消除是Java虚拟机在JIT编译期间，通过对运行上下文的扫描，去除不可能存在共享资源竞争的锁
     * 通过锁消除，可以节省毫无意义的请求锁时间。
     * <p>
     * -XX:+EliminateLocks 开启锁消除(jdk8默认开启）
     * -XX:-EliminateLocks 关闭锁消除
     */
    @Test
    public void test2() {
        StringBufferEntity demo = new StringBufferEntity();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            demo.append("aaa", "bbb");
        }
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - start) + " ms");
    }

    //  StringBuffer的append是个同步方法，但是append方法中的 StringBuffer 属于一个局部变量，不可能从该方法中逃逸出去，因此其实这过程是线程安全的，可以将锁消除。
    //  测试结果： 关闭锁消除执行时间 4415 ms   开启锁消除执行时间：1875 ms
}
