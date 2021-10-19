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
    static AtomicInteger ai = new AtomicInteger(10);
    public static void main(String[] args) {
        ai.getAndIncrement();
        ai.incrementAndGet();
        System.out.println(ai.get());
    }
}
