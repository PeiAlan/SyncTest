package com.example.shadow.demo6;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

//一个同步方法调用另外一个同步方法，能否得到锁?
//重入  synchronized默认支持重入
@Slf4j(topic = "enjoy")
public class Demo {

    synchronized void test1(){
        log.debug("test1 start.........");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test2();
    }

    /**
     * 为什么test2还需要加sync
     *
     * 他本身就包含在test1 而test1已经加了sync
     *
     * 为了防止其他线程在不执行test1的时候直接执行test2 而造成的线程不安全
     */
    synchronized void test2(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("test2 start.......");
    }



    public static void main(String[] args) {
        Demo demo= new Demo();
        demo.test1();
    }

}