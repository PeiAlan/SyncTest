package com.example.countDownLatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CountDownLatchTest2 {

    public static void main(String[] args) throws InterruptedException {
        //线程池里面创建4个线程  其中三个是计算的  第四个是汇总的
        AtomicInteger i = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(4, (zl) -> new Thread(zl, "t" + i.incrementAndGet()));

        CountDownLatch latch = new CountDownLatch(3);


        executorService.submit(() -> {
            log.debug("t1 thread start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            log.debug("t1 thread end;count[{}]", latch.getCount());
        });


        executorService.submit(() -> {
            log.debug("t2 thread start");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            log.debug("t2 thread end;count[{}]", latch.getCount());
        });

        executorService.submit(() -> {
            log.debug("t3 thread start");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            log.debug("t3 thread end;count[{}]", latch.getCount());
        });


        executorService.submit(() -> {
            log.debug("t4 watiing");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t4 wait end...");

        });

        executorService.shutdown();
    }
}
