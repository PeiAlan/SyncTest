package com.example.threadPool;

import com.example.guarded.GuardedObjectTimeOut;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/12/4 15:58
 * @since 1.0
 **/
@Slf4j
public class ThreadTest {

    private static Thread t1, t2;
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        GuardedObjectTimeOut guardedObjectTimeOut = new GuardedObjectTimeOut();
        t1 = new Thread(() -> {
            guardedObjectTimeOut.getResponse(10L);
        }, "t1");

        t2 = new Thread(() -> {
            guardedObjectTimeOut.setResponse("11111");
        }, "t2");
        t1.start();
        Thread.sleep(2000L);
        t2.start();
    }
}
