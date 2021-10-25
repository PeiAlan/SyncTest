package com.example.guarded;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Ellison Pei
 */
@Slf4j(topic = "enjoy")
public class TestJoinTimeOut {

    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        Thread t1 = new Thread(() -> {
            try {
                log.debug("t1");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        t1.start();
        //main线程 等待 t1执行完
        t1.join(2000);

        o.wait();

        log.debug("man end");

    }
}
