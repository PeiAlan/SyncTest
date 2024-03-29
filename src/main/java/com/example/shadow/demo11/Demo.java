package com.example.shadow.demo11;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ellison Pei
 */
public class Demo {
    private static Logger log = LogManager.getLogger(Demo.class);
    int count = 0;
    /** 相比较上一个例子，synchronized既保证了原子性又保证了可见性 */
    public synchronized void test(){
        for (int i = 0; i < 10000; i++) {
            count ++;
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(demo::test, "thread-" + i));
        }
        threads.forEach((o)->o.start());
        threads.forEach((o)->{
            try {
                o.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
       log.debug(demo.count+"");
    }

}
