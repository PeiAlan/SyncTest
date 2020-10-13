package com.shadow.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class TestThreadExecutorPool11 {

    static ThreadPoolExecutor threadPoolExecutor;
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        threadPoolExecutor = new ThreadPoolExecutor(1,3,
                3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(98));


        for (int i = 0; i <100 ; i++) {
            //执行这个任务的线程是什么线程？ 并不核心线程？
            /**
             * 核心线程和空闲线程测区别？
             * 1、空闲线程可能会销毁（存活时间）
             * 2、核心线程是不会销毁的
             *  50
             * 线程池是如何保证核心线程不会被销毁的---->空闲线程数为什么会销毁
             */
            threadPoolExecutor.execute(() -> {
                log.debug("worker thread size[{}]", threadPoolExecutor.getPoolSize());
            });


        }


    }

    private static void method1() {
        threadPoolExecutor = new ThreadPoolExecutor(1,2,
                3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5));


        for (int i = 0; i <6 ; i++) {
            //这里永远只有一个线程在工作？
            // 并发编程的艺术 -----因为队列能够存放的下任务所以不会启用空闲线程
            threadPoolExecutor.execute(()->{
                log.debug("worker thread size[{}]",threadPoolExecutor.getPoolSize());
            });
        }
    }


    static class MyTask implements Runnable {
        private int taskNum;

        public int getTaskNum() {
            return taskNum;
        }

        public MyTask(int num) {
            this.taskNum = num;
        }

        @Override
        public void run() {
            log.debug(getTaskNum()+"");
        }
    }
}
