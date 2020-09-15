package com.shadow.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "enjoy")
public class Test7 {

    int i=0;

    public static   void main(String[] args) throws InterruptedException {

    }


    public  void a(){
        i++;
    }


    public  void b(){
        Object o = new Object();
        synchronized (o) {
            i++;
        }
    }






}
