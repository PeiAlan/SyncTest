package com.example.poolv2;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class TestPool {
    public static void main(String[] args) {

        EnjoyThreadPool enjoyThreadPool = new EnjoyThreadPool(2,new A());


        for (int i = 1; i <6 ; i++) {
            enjoyThreadPool.submitTask(new CustomTask("task"+i));
        }


    }
}
