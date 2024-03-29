package com.example.shadow.demo13;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 一道面试题：实现一个容器，提供两个方法，add,size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，
 * 线程2给出提示并结束
 *
 * 这里虽然T2能够及时收到消息唤醒，但是wait会释放锁，notify不会释放锁，所以T1线程结束后
 * T2线程才执行完成
 */
public class Container3 {

    private static Logger log = LogManager.getLogger(Container3.class);
    volatile List lists = new ArrayList();

    public void add(Object o){
        lists.add(o);
    }

    public int size(){
        return lists.size();
    }

    public static void main(String[] args) {
        Container3 c = new Container3();
        Object lock = new Object();

        new Thread(()->{
            synchronized (lock) {
                log.debug("t2启动");
                if (c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                log.debug("t2结束");
            }
        }," t2").start();

        new Thread(()->{
            log.debug("t1启动");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    log.debug("add " + i);
                    if (c.size() == 5) {
                        // 这里和t2持有同一把锁（lock），虽然 notify() 了，但是没有释放锁，所以t2无法执行
                        // notify()方法不释放锁
                        lock.notify();
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();

    }

}
