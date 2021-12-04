package com.example.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 演示 空闲线程和核心线程的概念---空闲线程和核心线程都会从队列当中去获取任务 随机
 * 前提是空闲线程被启用
 *
 * 参数意义：
 * public ThreadPoolExecutor(int corePoolSize,                      核心线程数
 *                               int maximumPoolSize,               最大线程数（应急线程数||空闲线程）
 *                               long keepAliveTime,                针对空闲线程的存活时间 如果超时了则把空闲的线程kill
 *                               TimeUnit unit,                     针对3的时间单位
 *                               BlockingQueue<Runnable> workQueue, 任务存放的队列
 *                               ThreadFactory threadFactory,       线程工厂，主要是产生线程---给线程起个自定义名字
 *                               RejectedExecutionHandler handler)  拒绝策略
 *
 *                         线程池中的执行优先级：      corePoolSize  >  workQueue   >  空闲线程
 **/
@Slf4j
public class TestThreadExecutorPool {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        //懒惰性---不会再一开始就创建线程，他是有任务提交的时候才会创建线程
        ThreadPoolExecutor threadPoolExecutor
                = new ThreadPoolExecutor(1, 2,
                3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                (r) -> new Thread(r, "t" + atomicInteger.incrementAndGet()), new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 2; i++) {
            threadPoolExecutor.execute(new MyTask(i));
        }

        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(1, TimeUnit.HOURS);

    }


    static class MyTask implements Runnable {
        private int taskNum;

        public MyTask(int num) {
            this.taskNum = num;
        }

        @Override
        public void run() {
//            log.debug("线程数目{}", executor.getActiveCount());
            log.debug("线程名称：{} 正在执行task{}", Thread.currentThread().getName(), taskNum);
            try {
                Thread.sleep(1000);
                //log.debug("线程数目{}", executor.getActiveCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("task{}执行完毕============", taskNum);
        }
    }
}
