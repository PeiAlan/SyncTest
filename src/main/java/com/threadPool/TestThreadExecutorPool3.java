package com.shadow.threadPool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class TestThreadExecutorPool3 {
    static ThreadPoolExecutor threadPoolExecutor;
    @SneakyThrows
    public static void main(String[] args) {



        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<String> futureTeak = executorService.submit(() -> {
            log.debug("1");
            TimeUnit.SECONDS.sleep(1);
            return "success";
        });
        log.debug("start");
        log.debug("result[{}]",futureTeak.get());

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
