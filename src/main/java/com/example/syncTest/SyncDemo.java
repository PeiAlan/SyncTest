package com.example.syncTest;


import lombok.extern.slf4j.Slf4j;

/**
 * @author Ellison Pei
 */
@Slf4j
public class SyncDemo {

    /**
     * volatile 对于单一操作保持原子性，对于 ++  -- 这种操作不保证原子性
     */
    private static int counter = 0;

    public synchronized static void increment() {
        counter++;
    }

    /**
     * SyncDemo.class
     */
    public synchronized static void decrement() {
        counter--;
    }

    /**
     * 类的实例对象，  new SyncDemo()  == obj
     */
    public synchronized void test() {
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                decrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        //思考： counter=？
        log.info("counter={}", counter);


        //synchronized (SyncDemo.class){
        //
        //}
        //
        //synchronized (this){
        //
        //}
        //
        //Object o = new Object();
        //synchronized (o){
        //
        //}
    }
}
