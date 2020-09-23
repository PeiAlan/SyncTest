package com.rwlock;

import java.util.concurrent.locks.StampedLock;

/**
 * <p>读写锁的使用</p>
 *
 * @Author Ellison Pei
 * @Date 2020/9/20 20:11
 **/
public class StampedLockDataContainer {

    private final StampedLock lock = new StampedLock();


    public void read(){
        long stamp = lock.tryOptimisticRead();
    }

    public void write(){

    }
}
