package com.example.threadActiveness;

/**
 * @author
 * 哲学家就餐问题
 */
public class PhilosopherEatTest {

    public static void main(String[] args) {

        //初始化五根筷子
        Chopstick c1 = new Chopstick(1);
        Chopstick c2 = new Chopstick(2);
        Chopstick c3 = new Chopstick(3);
        Chopstick c4 = new Chopstick(4);
        Chopstick c5 = new Chopstick(5);
        // 思考： 如何打破循环
        new Philosopher("苏格拉底", c1, c2).start();
        new Philosopher("柏拉图", c2, c3).start();
        new Philosopher("亚里士多德", c3, c4).start();
        new Philosopher("赫拉克利特", c4, c5).start();
        new Philosopher("阿基米德", c1,c5).start();

    }
}
