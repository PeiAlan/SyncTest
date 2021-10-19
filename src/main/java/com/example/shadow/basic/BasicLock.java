package com.example.shadow.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ellison Pei
 */
@Slf4j(topic = "enjoy")
public class BasicLock {
    /**
     * 修饰方法，锁的是实例对象
     */
    public synchronized void x(){
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
