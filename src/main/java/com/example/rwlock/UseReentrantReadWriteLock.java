package com.example.rwlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/10/21 16:52
 * @since 1.0
 **/
@Slf4j
public class UseReentrantReadWriteLock {
    Object data;
    volatile boolean cacheValid;
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    void processCachedData() {
        rwl.readLock().lock();
        // 检查缓存是否过期
        if (!cacheValid) {
            // Must release read lock before acquiring write lock
            // 由于锁不能升级，这里手动更换为写锁，互斥锁
            rwl.readLock().unlock();
            rwl.writeLock().lock();
            try {
                // Recheck state because another thread might have
                // acquired write lock and changed state before we did.
                // 双重校验
                if (!cacheValid) {
                    // 读取数据库数据
                    log.debug("{}, 去数据库读取数据。。。。。。", Thread.currentThread().getName());
                    cacheValid = true;
                }
                // Downgrade by acquiring read lock before releasing write lock
                // 处理完后，写锁更换为读锁，读读并发
                rwl.readLock().lock();
            } finally {
                rwl.writeLock().unlock(); // Unlock write, still hold read
            }
        }

        try {
            //使用数据
            log.debug("{}, 使用数据数据。。。。。。", Thread.currentThread().getName());
        } finally {
            rwl.readLock().unlock();
        }
    }
}
