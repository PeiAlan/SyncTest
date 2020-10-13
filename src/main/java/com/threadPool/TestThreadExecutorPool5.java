package com.shadow.threadPool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class TestThreadExecutorPool5 {
    static ThreadPoolExecutor threadPoolExecutor;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(
                ()->{
                    log.debug("1");
                    return "1";
                },()->{
                    log.debug("2");
                    return "2";
                },()->{
                    log.debug("3");
                    return "3s";
                },()->{
                    log.debug("4");
                    return "4s";
                }
        ));
        log.debug("main start");
        futures.forEach(f->{
            try {
                log.debug(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        log.debug("main end");

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
