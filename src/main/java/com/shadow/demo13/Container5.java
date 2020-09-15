package com.shadow.demo13;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 一道面试题：实现一个容器，提供两个方法，add,size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，
 * 当个数到5个时，线程2给出提示并结束
 *
 * CountDownLatch
 * 使用await和countdown方法替代wait和notify
 * CountDownLatch不涉及锁定，当count的值为零时当前线程继续运行
 * 相当于是发令枪，运动员线程调用await等待，计数到0开始运行
 * 当不涉及同步，只是涉及线程通信的时候，用synchronized加wait，notify就显得太重了
 */
@Slf4j(topic = "enjoy")
public class Container5 {

    volatile List lists = new ArrayList();

    public void add(Object o){
        lists.add(o);
    }

    public int size(){
        return lists.size();
    }

    public static void main(String[] args) {
        Container5 c = new Container5();

        CountDownLatch latch = new CountDownLatch(1);

        String s = new String("XXXXX");
        new Thread(()->{
            log.debug("t2启动");
            if (c.size() != 5) {
                try {
                    //阻塞
                    latch.await();//准备
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.debug("t2结束");
            }
        }," t2").start();

        new Thread(()->{
            log.debug("t1启动");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                log.debug("add " + i);
                if (c.size() == 5) {
                    latch.countDown();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
    }

}
