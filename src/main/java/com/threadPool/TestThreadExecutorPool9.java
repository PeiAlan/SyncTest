package com.shadow.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author 钢牌讲师--子路
 * 定时任务
 **/
@Slf4j(topic = "e")
public class TestThreadExecutorPool9 {

   static ScheduledExecutorService scheduledExecutorService;
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        withFixedDelay();

    }


    //delay  正在的延迟执行
    private static void withFixedDelay() {
        scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleWithFixedDelay(()->{
            log.debug("start---task1");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },2,1,TimeUnit.SECONDS);
    }

    //period 是包含在线程的执行时间当中的
    private static void atFixedRate() {
        scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            log.debug("start---task1");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },2,1,TimeUnit.SECONDS);
    }

    private static void repeat() {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        log.debug("main start");
        scheduledExecutorService.schedule(()->{
            executeTask();
        },3, TimeUnit.SECONDS);
    }


    public static void executeTask(){
        log.debug("start---task1");

        scheduledExecutorService.schedule(()->{
            executeTask();
        },3, TimeUnit.SECONDS);

    }

    private static void delay() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        log.debug("main start");
        scheduledExecutorService.schedule(()->{
            log.debug("start---task1");
        },3, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
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
            log.debug("task1--start----");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("task1--end----");
        }
    }
}
