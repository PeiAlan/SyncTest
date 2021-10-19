package com.example.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 来限制对资源访问的线程上线
 */
@Slf4j(topic = "enjoy")
public class SemaphoreTest {
    public static void main(String[] args) {

        //每次访问的线程上限是3
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 15; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("start...");
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("end...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
