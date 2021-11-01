package com.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Ellison Pei
 */
@Slf4j
public class LockTest3 {

    /** 首先定义一把锁 */
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
//        lock1();

        String s = "PL1641-20211028100116_PL.csv";

        System.out.println(s.substring(3, s.indexOf("-")));
    }


    public static void lock1() {
        lock.lock();
        try {
            log.debug("执行lock1");
            //重入
            lock2();
        } finally {
            lock.unlock();
        }
    }

    public static void lock2() {
        lock.lock();
        try {
            log.debug("执行lock2");
        } finally {
            lock.unlock();
        }
    }

}
