package com.example.threadPool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j
public class TestThreadExecutorPool3 {
    static ThreadPoolExecutor threadPoolExecutor;

    @SneakyThrows
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<String> futureTask = executorService.submit(() -> {
            log.debug("1");
            TimeUnit.SECONDS.sleep(1);
            return "success";
        });
        log.debug("start");
        log.debug("result[{}]", futureTask.get());
//        关闭任务
        executorService.shutdown();
//        关闭执行器
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        log.debug("end");
    }


    static class MyTask implements Runnable {
        private int taskNum;

        public MyTask(int num) {
            this.taskNum = num;
        }

        @Override
        public void run() {

        }
    }
}
