package com.jmm;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class Test6 {
    //hotspot  某种技术  可见性
    // J9
    static int a=0;
    static  volatile  boolean flag =false;





    @SneakyThrows
    public static void main(String[] args) {


        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    a = 50;
                    flag = true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);

        for(;;){
            if(a==50){
                log.debug(""+a);
                thread.interrupt();
                break;
            }
        }



    }
}
