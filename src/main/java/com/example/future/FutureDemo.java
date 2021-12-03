package com.example.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Fox
 */
@Slf4j
public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        new Thread(() -> {
            log.debug("通过Runnable方式执行任务");
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();


        FutureTask task = new FutureTask<>(new Callable() {
            @Override
            public Object call() throws Exception {
                log.debug("通过Callable方式执行任务");
                Thread.sleep(3000);
                return "返回任务结果";
            }

        });

        new Thread(task).start();
        log.debug("结果：{}", task.get());

    }
}
