package com.example.syncTest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ellison Pei
 */
@Slf4j
public class SyncWaitTest {
    private static int counter = 0;

    private final Object lock = new Object();

    public void test() {
        log.debug(Thread.currentThread().getName() + " start");
        synchronized (lock) {
            log.debug(Thread.currentThread().getName() + " execute");
            try {
                //Thread.sleep(5000);
                lock.wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug(Thread.currentThread().getName() + " end");
        }

    }

    public void test2() {
        log.debug(Thread.currentThread().getName() + " start");
        synchronized (lock) {
            log.debug(Thread.currentThread().getName() + " execute");
            lock.notifyAll();
            log.debug(Thread.currentThread().getName() + " end");
        }

    }


    public static void main(String[] args) {
        SyncWaitTest test = new SyncWaitTest();

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test.test();
                }
            }, "thread" + i).start();
        }

    }


}
