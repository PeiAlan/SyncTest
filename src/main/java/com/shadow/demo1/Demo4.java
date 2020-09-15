package com.shadow.demo1;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ellison")
public class Demo4 {
    private static int count = 10;
    //synchronize关键字修饰静态方法锁定的是类的对象
    public synchronized static void test(){
        count--;
        log.debug(Thread.currentThread().getName() + " count = " + count);
    }
    public static void test2(){
        synchronized (Demo4.class){//这里不能替换成this
            count--;
        }
    }
}
