package com.shadow.demo2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 同步代码快中的语句越少越好
 * 比较test1和test2
 * 业务逻辑中只有count++这句需要sync，这时不应该给整个方法上锁
 * 采用细粒度的锁，可以使线程争用时间变短，从而提高效率
 */
@Slf4j(topic = "ellison")
public class Demo3 {
    int count = 0;
    public synchronized void test1(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count ++;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 局部加锁
     */
    public void test2(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            count ++;
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
