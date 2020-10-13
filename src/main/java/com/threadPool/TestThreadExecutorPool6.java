package com.shadow.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class TestThreadExecutorPool6 {
    static ThreadPoolExecutor threadPoolExecutor;

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        int i  = executorService.invokeAny(Arrays.asList(
                () -> {
                    TimeUnit.MILLISECONDS.sleep(3000);
                    log.debug("1");
                    return 11;
                }, () -> {
                    TimeUnit.MILLISECONDS.sleep(500);
                    log.debug("2");

                    return 2;
                }, () -> {
                    TimeUnit.MILLISECONDS.sleep(2000);
                    log.debug("3");
                    return 3;
                }, () -> {
                    TimeUnit.MILLISECONDS.sleep(2000);
                    log.debug("4");
                    return 4;
                }
        ));
        log.debug("main start");
        log.debug(i+"");
//        future.forEach(f->{
//            try {
//                log.debug(f.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
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
