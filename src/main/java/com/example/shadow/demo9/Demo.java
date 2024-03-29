package com.example.shadow.demo9;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile 关键字，使一个变量在多个线程间可见
 * mian,t1线程都用到一个变量，java默认是T1线程中保留一份副本，这样如果main线程修改了该变量，
 * t1线程未必知道
 * <p>
 * 使用volatile关键字，会让所有线程都会读到变量的修改值
 * <p>
 * 在下面的代码中，running是存在于堆内存的t对象中
 * 当线程t1开始运行的时候，会把running值从内存中读到t1线程的工作区，在运行过程中直接使用这个副本，
 * 并不会每次都去读取堆内存，这样，当主线程修改running的值之后，t1线程感知不到，所以不会停止运行
 * <p>
 * <p>
 * 但是这可能是个错误
 */
public class Demo {
    private static Logger log = LogManager.getLogger(Demo.class);
    boolean running = true;
    List<String> list = new ArrayList<>();

    /**
     * t1线程
     */
    public void test() {
        log.debug("testJol start...");
        boolean flag = running;
        while (running) {
            log.debug("... ing ...");
        }
        log.debug("testJol end...");
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        new Thread(demo::test, "t1").start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        demo.running = false;
    }

}