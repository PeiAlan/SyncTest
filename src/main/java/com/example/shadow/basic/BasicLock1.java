package com.example.shadow.basic;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ellison")
public class BasicLock1 {
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
     * 修饰方法，锁的是实例对象
     */
    public synchronized void y(){
        log.debug("y");
    }
    public void z(){
        log.debug("z");
    }
}
