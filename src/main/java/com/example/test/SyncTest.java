package com.example.test;

import java.util.concurrent.TimeUnit;

public class SyncTest {

    private static Thread thread1, thread2;
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始执行===============");
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "拿到锁！");
                try {
                    System.out.println(Thread.currentThread().getName() + "开始wait()！");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "执行逻辑。。。。");
            }
        }, "thread1");

        thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始执行===============");
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "拿到锁，执行逻辑...");
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + "开始随机唤醒其他线程!");
                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread2");

        thread1.start();
        Thread.sleep(10000);
        thread2.start();
    }
}
