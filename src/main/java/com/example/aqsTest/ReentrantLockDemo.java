package com.example.aqsTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author
 * 同步执行
 */
public class ReentrantLockDemo {

    private static  int sum = 0;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(()->{
                //加锁
                lock.lock();
                try {
                    // 临界区代码
                    // TODO 业务逻辑：读写操作不能保证线程安全
                    for (int j = 0; j < 10000; j++) {
                        sum++;
                    }
//                    TimeUnit.MINUTES.sleep(60);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 解锁
                    lock.unlock();
                }
            }, "t" + i);
            thread.start();
        }

        Thread.sleep(2000);
        System.out.println(sum);
    }
}
