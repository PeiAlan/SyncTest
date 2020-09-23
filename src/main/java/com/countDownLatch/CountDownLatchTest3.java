package com.countDownLatch;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j(topic = "enjoy")
public class CountDownLatchTest3 {

    public static void main(String[] args) throws InterruptedException {
        //线程池里面创建3个线程
        List<String> list = new ArrayList<>();
        list.add("Angel");
        list.add("baby");
        list.add("rose");
        list.add("joyce");

        AtomicInteger i= new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(4,(runnable)->{
            //技师的名字
            return new Thread(runnable,list.get(i.getAndIncrement()));
        });

        //让你先去沙发上休息
        CountDownLatch latch = new CountDownLatch(4);
        Random random = new Random();
        for (int j = 0; j <4 ; j++) {//new 4个线程  并发执行
            int temp =j;
            executorService.submit(()->{
                //k标识的是准备进度 直到准备到100% 才开始服务  这个时间每个技师不固定  因为是random
                for (int k = 0; k <100 ; k++) {
                    try {
                        //模拟每一个技师准备的时间
                        TimeUnit.MILLISECONDS.sleep(random.nextInt(200));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String name = Thread.currentThread().getName();
                    name=name+"("+k+"%)";//angel(3%) baby(10%) ...
                    list.set(temp,name);
                    System.out.print("\r"+Arrays.toString(list.toArray()));
                }
                //某个人准备好了
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("\n 登上人生巅峰...");
        executorService.shutdown();
    }
}
