package com.shadow.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 钢牌讲师--子路
 * 演示 空闲线程和核心线程的概念---空闲线程和核心线程都会从队列当中去获取任务 随机
 * 前提是空闲线程被弃用
 **/
@Slf4j(topic = "e")
public class TestThreadExecutorPool {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        //懒惰性---不会再一开始就创建线程，他是有任务提交的时候才会创建线程
        ThreadPoolExecutor threadPoolExecutor
                = new ThreadPoolExecutor(1,2,
                3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                (r)->{
                    return  new Thread(r,"t"+atomicInteger.incrementAndGet());
                },new ThreadPoolExecutor.AbortPolicy());


        for (int i = 0; i <2 ; i++) {
            threadPoolExecutor.execute(new MyTask(i));
        };

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
                Thread.currentThread().sleep(1000);
                //log.debug("线程数目{}", executor.getActiveCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("task{}执行完毕============", taskNum);
        }
    }
}
