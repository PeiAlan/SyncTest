package com.shadow.demo5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 脏读问题
 * 实际业务当中应该看是否允许脏读，
 * 不允许的情况下对读方法也要加锁
 */
@Slf4j(topic = "enjoy")
public class Demo {

    //卡的持有人
    String name;

    //卡上的余额
    double balance;

    /**
     *
     * @param name
     * @param balance
     */
    public synchronized void set(String name,double balance){
        this.name = name;
        try {
            //模拟存钱耗时
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public synchronized double getBalance(String name){
        return this.balance;
    }

    public static void main(String[] args) {
        Demo demo = new Demo();

        //2s
        new Thread(()->demo.set("zl",100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //1s之后 结果 0
        log.debug(demo.getBalance("zl")+"");//

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //3s之后就算100
        log.debug(demo.getBalance("zl")+"");
    }

}
