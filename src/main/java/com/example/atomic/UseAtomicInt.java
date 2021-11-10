package com.example.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/10/7 14:20
 * @since 1.0
 **/
public class UseAtomicInt {
    static AtomicInteger sum = new AtomicInteger(0);

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    // 原子自增  CAS
                    sum.incrementAndGet();
                    //TODO
                }
            });
            thread.start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sum.get());

    }
}
