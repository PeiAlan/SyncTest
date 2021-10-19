package com.example.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author
 * SynchronousQueue 可以说是没有容量的
 *      t1线程put完数据后，其他线程必须消费完，才放第二个数据，否则一直阻塞
 **/

@Slf4j(topic = "e")
public class SynchronousQueueTest {
    static  boolean flag=true;
    public static void main(String[] args) throws InterruptedException {

        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        new Thread(()->{
            log.debug("start put 1");
            try {
                synchronousQueue.put("1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end put 1");


            log.debug("start put 2");
            try {
                synchronousQueue.put("2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end put 2");

        },"t1").start();






        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            log.debug("start take 1");
            try {
                synchronousQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("end take 1");


        },"t2").start();
    }
}
