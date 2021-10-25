package com.example.shadow.demo12;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * atomicXXX
 * 一道面试题：多个atomic类连续调用能否构成原子性?  不能
 */
public class Demo {
    private static Logger log = LogManager.getLogger(Demo.class);
    AtomicInteger count = new AtomicInteger(0);

    public void test(){
        for (int i = 0; i < 10000; i++) {
            // 这里的 count.get() 和 count.incrementAndGet() 中的count没有保证原子性，所以不会加到1000就停止
            // cas 指令只能单条指令使用，多条指令是不能保证原子性的
            if(count.get() < 1000){
                //count++
                count.incrementAndGet();
            }
        }
    }
    public static void main(String[] args) {
        Demo demo = new Demo();
        List<Thread> threads = new ArrayList();
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
