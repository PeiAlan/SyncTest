package com.example.jmm;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/11/21 15:46
 * @since 1.0
 **/
@Slf4j
public class Test {
    private volatile boolean flag = true;

    public void refresh() {
        flag = false;
        log.debug(Thread.currentThread().getName() + "修改flag");
    }

    public void load() {
        log.debug(Thread.currentThread().getName() + "开始执行.....");
        int i = 0;
        while (flag) {
            i++;
            //TODO  业务逻辑

        }
        log.debug(Thread.currentThread().getName() + "跳出循环: i=" + i);
    }

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();

        // 线程threadA模拟数据加载场景
        Thread threadA = new Thread(() -> test.load(), "threadA");
        threadA.start();

        // 让threadA执行一会儿
        Thread.sleep(1000);
        // 线程threadB通过flag控制threadA的执行时间
        Thread threadB = new Thread(() -> test.refresh(), "threadB");
        threadB.start();

    }


    public static void shortWait(long interval) {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (start + interval >= end);
    }
}
