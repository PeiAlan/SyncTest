package com.example.stampedLock;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * <p>TODO</p>
 *
 * @Author Ellison Pei
 * @Date 2020/9/24 14:11
 **/
@Slf4j
public class StampedLockRWTest {
    public static void main(String[] args) {
        Container container = new Container();
        container.setI(1);

        //线程T1  读
        new Thread(() -> {
            container.read();
        }, "t1").start();
        //线程T2  读
        new Thread(() -> {
            container.read();
        }, "t2").start();

        //线程T3  写
        new Thread(() -> {
            container.write(210);
        }, "t3").start();

    }
}

@Slf4j
class Container {
    int i;
    //提供 戳  的功能，read之前获取戳，判断戳是否被改变
    long stampRw = 0l;

    public void setI(int i) {
        this.i = i;
    }

    private final StampedLock lock = new StampedLock();

    @SneakyThrows
    public int read() {
        //尝试乐观读
        long stamp = lock.tryOptimisticRead();
        log.debug("StampedLock 读锁拿到锁{}", stamp);
        //验证版本戳   //验证成功
        if (lock.validate(stamp)) {
            log.debug("StampedLock 验证完毕stamp{}, data.i:{}", stamp, i);
            TimeUnit.SECONDS.sleep(10);
            return i;
        }
        //一定验证失败
        log.debug("验证失败 被写线程t3给改变了{}", stampRw);
        try {
            //锁的升级 也会改戳
            stamp = lock.readLock();
            log.debug("升级之后的加锁成功 {}", stamp);
            TimeUnit.SECONDS.sleep(1);
            log.debug("升级读锁完毕{}, data.i:{}", stamp, i);
            return i;
        } finally {
            log.debug("升级锁解锁 {}", stamp);
            lock.unlockRead(stamp);
        }

    }

    @SneakyThrows
    public void write(int i) {
        //cas 加鎖
        stampRw = lock.writeLock();
        log.debug("写锁加锁成功 {}", stampRw);
        try {
            TimeUnit.SECONDS.sleep(5);
            this.i = i;
        } finally {
            log.debug("写锁解锁 {},data.i{}", stampRw, i);
            lock.unlockWrite(stampRw);
        }
    }

}
