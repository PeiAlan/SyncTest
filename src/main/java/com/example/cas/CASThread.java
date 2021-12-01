package com.example.cas;


import com.example.threadPool.CustomThreadFactory;
import org.apache.commons.lang3.time.StopWatch;

import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/12/1 10:01
 * @since 1.0
 **/
public class CASThread implements Runnable{

    private AtomicInteger total;
    private CountDownLatch latch;

    public CASThread(AtomicInteger total, CountDownLatch latch) {
        this.total = total;
        this.latch = latch;
    }

    @Override
    public void run() {
        while (!total.compareAndSet(total.get(), total.get() + 1)){}
        latch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(1000);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1000, 1000,
                1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(300), CustomThreadFactory.create("CASThread"));
        for (int i = 0; i < 1000; i ++) {
            executor.execute(new CASThread(atomicInteger, latch));
        }
        latch.await();
        System.out.println(atomicInteger.get());
        System.out.println("消耗：" + stopWatch.getTime() + "ms");
        LinkedList<String> list = new LinkedList<>();
        CompletableFuture.runAsync(() -> new Object());
    }
}