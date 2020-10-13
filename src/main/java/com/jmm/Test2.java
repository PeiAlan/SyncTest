package com.jmm;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author 钢牌讲师--子路
 **/

@Slf4j(topic = "e")
public class Test2 {
    public static void main(String[] args) throws InterruptedException {

        MyThread t = new MyThread();
        t.start();

        while(true){
            if(t.getFlag()){
                System.out.println("主线程进入循环执行~~~~~");
            }
        }
    }
}

class MyThread extends Thread{

    private boolean flag = false;

    @SneakyThrows
    public void run() {
        TimeUnit.SECONDS.sleep(1);
        flag = true;
        System.out.println("flag="+flag);
    }
    public boolean getFlag() throws InterruptedException {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

