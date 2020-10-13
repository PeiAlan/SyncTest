package com.shadow.threadPool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class TestThreadExecutorPool4 {
    static ThreadPoolExecutor threadPoolExecutor;

    public static void main(String[] args) {



        ExecutorService executorService = Executors.newFixedThreadPool(1);

       executorService.submit(() -> {

            log.debug("1{}",1/0);
//           try {
//               TimeUnit.SECONDS.sleep(1);
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }

       });

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
