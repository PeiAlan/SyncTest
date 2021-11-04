package com.example.cyclic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ellison Pei
 */
@Slf4j
public class CyclicBarrierTest2 {
    public static void main(String[] args) {
        Object o = new Object();
        AtomicInteger i = new AtomicInteger();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            log.debug("xxxxxxx");
        });
        ExecutorService service = Executors.newFixedThreadPool(2, (r) -> new Thread(r, "t" + i.getAndIncrement()));


        for (int j = 0; j < 2; j++) {
            service.submit(() -> {
                log.debug("start");
                try {
                    TimeUnit.SECONDS.sleep(1);
                    cyclicBarrier.await();
                    log.debug("working");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            });


            service.submit(() -> {
                log.debug("start");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    cyclicBarrier.await();
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("working");

                } catch (Exception e) {
                    e.printStackTrace();
                }


            });

        }

        service.shutdown();

    }
}
