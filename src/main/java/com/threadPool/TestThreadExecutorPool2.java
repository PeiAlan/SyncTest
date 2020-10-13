package com.shadow.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 钢牌讲师--子路
 * 演示 空闲线程超时的问题
 * 存活的线程
 **/
@Slf4j(topic = "e")
public class TestThreadExecutorPool2 {
    static ThreadPoolExecutor threadPoolExecutor;
    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        //懒惰性---不会再一开始就创建线程，他是有任务提交的时候才会创建线程

        threadPoolExecutor= new ThreadPoolExecutor(1,2,
                3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                (r)->{
                    return  new Thread(r,"t"+atomicInteger.incrementAndGet());
                },new ThreadPoolExecutor.AbortPolicy());


        for (int i = 0; i <3 ; i++) {
            //start t1
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
            log.debug("当前线程池线程数目-----{}", threadPoolExecutor.getPoolSize());//1||2
            //log.debug("线程名称：{} 正在执行task{}", Thread.currentThread().getName(), taskNum);
            try {
                // core 抢走了队列当中的task  4sing  t2 idle 一直空闲--timeout --回收
                Thread.currentThread().sleep(1000);//即使t2执行完了 也是两个
               // threadPoolExecutor.getCorePoolSize()
                log.debug("线程数目{}", threadPoolExecutor.getPoolSize());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //log.debug("task{}执行完毕============", taskNum);
        }
    }
}
