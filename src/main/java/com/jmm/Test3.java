package com.jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 钢牌讲师--子路
 **/
public class Test3 {
    public static void main(String[] args) {
        // 1.创建一个线程任务对象
        Runnable target = new ThreadTarget();
        // 2.开始100个线程对象执行这个任务。
        for(int i = 1 ; i <= 100 ; i++ ) {
            new Thread(target,"第"+i+"个线程").start();
        }
    }
}

// 线程任务类
class ThreadTarget implements Runnable{
    private AtomicInteger atomicInteger = new AtomicInteger();
    // 定义一个共享变量
   // private volatile int count = 0 ;
    @Override
    public void run() {
        synchronized (this) {

            for (int i = 1; i <= 10000; i++) {
                //System.out.println(Thread.currentThread().getName() + "count =========>>>> " + count);
            }
        }
    }
}
