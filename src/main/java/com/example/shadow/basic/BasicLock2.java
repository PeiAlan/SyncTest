package com.example.shadow.basic;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicLock2 {

    /**
     * 修饰static方法，锁的是当前的类对象
     */
    public synchronized static void x(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("x");
    }

    /**
     * 修饰static方法，锁的是当前的类对象
     */
    public synchronized static void y(){
        log.debug("y");
    }

    //无锁
    public void z(){
        log.debug("z");
    }
}
