package com.example.cyclic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j(topic = "enjoy")
public class CyclicBarrierTest {
    public static void main(String[] args) {
        AtomicInteger i= new AtomicInteger();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2,()->{

            log.debug("t1 t2 end");
        });


        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int j = 0; j <2 ; j++) {
            service.submit(()->{
                log.debug("start");
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("working");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            });

            service.submit(()->{
                log.debug("start");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    log.debug("working");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}
