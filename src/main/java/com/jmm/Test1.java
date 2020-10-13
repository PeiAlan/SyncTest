package com.jmm;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author 钢牌讲师--子路
 *
 * x=0; y=1
 *
 * x=1;y=0
 *
 * x=1;y=1
 *
 *
 * CPU调度 以及上下文切换
 *
 *
 *
 * x=0；y=0
 * 说明CPU进行了指令重排序
 *
 **/

@Slf4j(topic = "e")
public class Test1 {

    public static int a = 0 , b = 0;


    public volatile static int x = 0 , y = 0;

    @SneakyThrows
    public static void main(String[] args) {
        //计数 看第几次出现优化
        int count = 0;


        while (true) {
            count++;
            a = 0;
            b = 0;
            x = 0;
            y = 0;

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            //都可以调度
            t1.start();
            t2.start();

            t1.join();
            t2.join();

            // 得到线程执行完毕以后 变量的结果。
           log.debug("第{}次输出结果：x ={}, y ={} ",count,x,y);
            if(x == 0 && y == 0){
                break;
            }
        }


    }
}
