package com.shadow.demo13;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 一道面试题：实现一个容器，提供两个方法，add,size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，
 * 当个数到5个时，线程2给出提示并结束线程2
 *
 * 这里list在两个线程之间不保证可见性，所以线程2始终结束不了
 */
@Slf4j(topic = "enjoy")
public class Container1 {

    List lists = new ArrayList();

    public void add(Object o){
        lists.add(o);
    }

    public int size(){
        return lists.size();
    }

    public static void main(String[] args) {
        Container1 c = new Container1();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                log.debug("add " + i);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }," t1").start();

        new Thread(()->{
            while (true) {
                if (c.size() == 5) {
                    break;
                }
            }
            log.debug("t2线程结束");
        }, "t2").start();
    }

}
