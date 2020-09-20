package com.rwlock;

import lombok.extern.slf4j.Slf4j;
/**
 * <p>ReentrantReadWriteLock锁升降级测试</p>
 * ReentrantReadWriteLock不支持锁的升级（其它锁可以支持）
 * 主要是避免死锁
 * 例如两个线程A和B都在读，A升级要求B释放读锁，B升级要求A释放读锁，互相等待形成死循环。
 * 如果能严格保证每次都只有一个线程升级那也是可以的
 *
 * @Author Ellison Pei
 * @Date 2020/9/16 11:37
 **/
@Slf4j(topic = "ellison")
public class ReentrantLockRWTest1 {
    private static java.util.concurrent.locks.ReentrantReadWriteLock reentrantLock = new java.util.concurrent.locks.ReentrantReadWriteLock();
    private static java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock readLock = reentrantLock.readLock();
    private static java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock writeLock = reentrantLock.writeLock();

    public static void read() {
        log.debug(Thread.currentThread().getName() + "开始尝试获取读锁");
        readLock.lock();
        try {
            log.debug(Thread.currentThread().getName() + "获取读锁，开始执行");
            Thread.sleep(20);
            log.debug(Thread.currentThread().getName()+ "尝试升级读锁为写锁");
            //读锁升级为写锁(失败)
            writeLock.lock();
            log.debug(Thread.currentThread().getName() +"读锁升级为写锁成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            log.debug(Thread.currentThread().getName() + "释放读锁");
        }
    }

    public static void write() {
        log.debug(Thread.currentThread().getName() + "开始尝试获取写锁");
        writeLock.lock();
        try {
            log.debug(Thread.currentThread().getName() + "获取写锁，开始执行");            Thread.sleep(40);
            log.debug(Thread.currentThread().getName() +"尝试降级写锁为读锁");
            //写锁降级为读锁（成功）
            readLock.lock();
            log.debug(Thread.currentThread().getName()+ "写锁降级为读锁成功");
            log.debug("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.debug(Thread.currentThread().getName() + "释放写锁");           
            writeLock.unlock();        
            readLock.unlock();
        }
    }
    public static void main(String[] args) {new Thread(() -> write(), "Thread1").start();
        new Thread(() -> read(), "Thread2").start();
    }
}
