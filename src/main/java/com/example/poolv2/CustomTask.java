package com.example.poolv2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class CustomTask implements Runnable{
    String name;

    public CustomTask(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("==========================================-task-------{}",name);
    }
}