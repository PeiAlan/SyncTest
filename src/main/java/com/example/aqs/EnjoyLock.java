package com.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
public class EnjoyLock implements Lock {

    EnjoySync enjoySync = new EnjoySync();
    /**
     * 加锁---阻塞式加锁
     */
    @Override
    public void lock() {
        enjoySync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return enjoySync.tryAcquire(1);

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        enjoySync.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        EnjoyLock enjoyLock = new EnjoyLock();

        new Thread(()->{
            enjoyLock.lock();
            log.debug("-----------");
            enjoyLock.unlock();

        },"t1").start();

        enjoyLock.lock();
        log.debug("main");
        TimeUnit.SECONDS.sleep(2);
        enjoyLock.unlock();
    }
}
