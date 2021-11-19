package com.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>TODO</p>
 *
 * @Author Ellison Pei
 * @Date 2020/9/15 21:06
 **/
@Slf4j
public class ReentrantLockTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                log.debug("t1----");
            } finally {
                lock.unlock();
                ;
            }
        }, "t1");

        t1.start();

        lock.lock();
        log.debug("main-----");
        lock.unlock();
    }
}
