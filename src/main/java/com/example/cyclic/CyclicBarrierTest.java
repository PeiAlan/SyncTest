package com.example.cyclic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CyclicBarrier 的字面意思是可循环使用（Cyclic）的屏障（Barrier）。
 * 它要做 的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一 个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续运行。
 * CyclicBarrier 默认的构造方法是 CyclicBarrier（int parties），其参数表示屏障拦截 的线程数量，每个线程调用 await 方法告诉 CyclicBarrier 我已经到达了屏障，然 后当前线程被阻塞。
 * CyclicBarrier 还提供一个更高级的构造函数 CyclicBarrie（r int parties，Runnable barrierAction），用于在线程到达屏障时，优先执行 barrierAction，方便处理更复 杂的业务场景。
 *
 * <p>CyclicBarrier 可以用于多线程计算数据，最后合并计算结果的场景。</p>
 *
 * 区别：
 * CountDownLatch 的计数器只能使用一次，而 CyclicBarrier 的计数器可以反复使用。
 * CountDownLatch.await 一般阻塞工作线程，所有的进行预备工作的线程执行 countDown，而 CyclicBarrier 通过工作线程调用 await 从而自行阻塞，直到所有工 作线程达到指定屏障，再大家一起往下走。
 * 在控制多个线程同时运行上，CountDownLatch 可以不限线程数量，而 CyclicBarrier 是固定线程数。 同时，CyclicBarrier 还可以提供一个 barrierAction，合并多线程计算结果。
 * @author Ellison Pei
 */
@Slf4j
public class CyclicBarrierTest {
    public static void main(String[] args) {
        AtomicInteger i= new AtomicInteger();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2,()->{
            log.debug("t1 t2 end");
        });

        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int j = 0; j <2 ; j++) {
            service.submit(()->{
                log.debug("start");
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("working");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            service.submit(()->{
                log.debug("start");
                try {
                    TimeUnit.SECONDS.sleep(3);
                    log.debug("working");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}
