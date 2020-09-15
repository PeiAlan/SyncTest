package com.shadow.demo1;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.TRANSACTION_MODE;
import sun.security.krb5.internal.crypto.Des;

@Slf4j(topic = "enjoy")
public class Demo2 {
    private int count = 10;
    public  void test(){
        //synchronized(this)锁定的是当前类的实例,这里锁定的是Demo2类的实例
        synchronized (this){
            count--;
            log.debug(Thread.currentThread().getName() + " count = " + count);
        }
    }
    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        new Thread(demo2::test,"t1").start();

        //synchronized(this)锁定的是当前类的实例
        //实例不同  锁的对象就不同
        Demo2 demo21 = new Demo2();
        new Thread(demo21::test, "t2").start();
    }
}
