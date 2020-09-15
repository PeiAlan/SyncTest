package com.shadow.demo3;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "enjoy")
public class Demo2 implements Runnable{

    private int count = 10;

    @Override
    public synchronized void run() {
        count--;
        log.debug(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            //相比较Demo1，这里是new了五个对象，每个线程对应都拿到各自的锁标记，可以同时执行。
            Demo2 demo = new Demo2();
            new Thread(demo,"t" + i).start();
        }
    }

}
