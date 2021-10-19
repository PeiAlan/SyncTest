package com.example.shadow.demo4;

import lombok.extern.slf4j.Slf4j;

//同步方法和非同步方法是否可以同时调用？ 可以
@Slf4j(topic = "enjoy")
public class Demo{
    public synchronized void test1(){
        log.debug(Thread.currentThread().getName() + " test1 start...");
        try {
            //睡眠5s 由于还要t2要执行 cpu回去执行t2
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug(Thread.currentThread().getName() + " test1 end...");
    }

    public void test2(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " test2");
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        //正在执行一个同步方法  没有释放锁
        new Thread(demo :: test1,"t1").start();
        //不影响其他线程执行非同步方法(就算他是一个同步方法，如果锁的不是同一个对象也不影响)
        new Thread(demo :: test2,"t2").start();
    }

}