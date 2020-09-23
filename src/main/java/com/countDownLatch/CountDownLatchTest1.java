package com.countDownLatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "enjoy")
public class CountDownLatchTest1 {

    public static void main(String[] args) throws InterruptedException {
         // ExecutorService executorService = Executors.newFixedThreadPool(3);
        // executorService


        //计数器=3
        CountDownLatch latch = new CountDownLatch(3);



        Thread thread = new Thread(() -> {
            log.debug("t1 thread start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //t1 把计数器-1
            latch.countDown();
            log.debug("t1 thread end;count[{}]", latch.getCount());
        }, "t1");
        thread.start();

        new Thread(() -> {
            log.debug("t2 thread start");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            log.debug("t2 thread end;count[{}]", latch.getCount());
        },"t2").start();

        new Thread(() -> {
            log.debug("t3 thread start");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();//主线程可以执行了
            log.debug("t3 thread end;count[{}]", latch.getCount());

        },"t3").start();



        log.debug("main watiing");
        latch.await();

        log.debug("main wait end...");
    }
}
