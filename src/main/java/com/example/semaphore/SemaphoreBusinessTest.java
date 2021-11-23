package com.example.semaphore;

import com.example.threadPool.CustomThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore  的 应用场景
 *
 * @author Ellison_Pei
 * @date 2021/11/22 15:45
 * @since 1.0
 **/
@Slf4j
public class SemaphoreBusinessTest {
    /**
     * 实现一个同时只能处理5个请求的限流器
     */
    private static Semaphore semaphore = new Semaphore(5);

    /**
     * 定义一个线程池
     */
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 50,
            60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(200), CustomThreadFactory.create("Test Semaphore"));

    /**
     * 模拟执行方法
     */
    public static void exec() {
        try {
            semaphore.acquire(1);
            // 模拟真实方法执行
            log.debug("执行exec方法");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release(1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        {
            for (; ; ) {
                Thread.sleep(100);
                // 模拟请求以10个/s的速度
                executor.execute(SemaphoreBusinessTest::exec);
            }
        }
    }
}
