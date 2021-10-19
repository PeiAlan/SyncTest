package com.example.stampedLock;

import java.util.concurrent.TimeUnit;

public class StampedLockTest {

    public static void main(String[] args) throws InterruptedException {
        //实例化数据容器
        DataContainer dataContainer = new DataContainer();
        //给了一个初始值  不算写 构造方法赋值
        dataContainer.setI(1);

        //读取
        new Thread(() -> {
            dataContainer.read();
        }, "t1").start();


//        new Thread(() -> {
//            dataContainer.read();
//        }, "t2").start();

        TimeUnit.SECONDS.sleep(3);
        new Thread(() -> {
            dataContainer.write(9);
        }, "t2").start();
    }
}
