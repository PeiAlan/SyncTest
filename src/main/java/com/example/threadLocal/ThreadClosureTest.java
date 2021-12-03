package com.example.threadLocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 线程封闭
 *
 * @author Ellison_Pei
 * @date 2021/12/2 13:49
 * @since 1.0
 **/
@Slf4j
public class ThreadClosureTest {

    public  static ThreadLocal<String> value = new ThreadLocal<>();

    @Test
    public void threadLocalTest() throws InterruptedException {
        value.set("123");
        String v = value.get();
        log.debug("线程1执行之前，主线程取到的值："+ v);
        new Thread(() -> {
            String value1 = value.get();
            log.debug("线程1取到的值"  + value1);
            // 线程1设置 threadLocal
            value.set("456");
            value1 = value.get();
            log.debug("线程1修改后，再次取到的值"  + value1);
            log.debug("线程1结束");
        }, "thread1").start();

        Thread.sleep(5000L);
        v = value.get();
        log.debug("线程1执行结束后，主线程取到的值："+ v);
    }
}
