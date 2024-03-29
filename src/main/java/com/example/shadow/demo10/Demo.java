package com.example.shadow.demo10;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 比如说第一个线程加到100了，还没往上加，另外一个线程来了，把100拿过来执行方法，
 * 然后第一个线程继续加到101，第二个线程也加到101，他两往回写都是101，线程不会管你加到哪儿了，
 * 虽然说加了2但是实际上只加了1.
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，
 * 也就是说volatile不能替代synchronized或者说volatile保证不了原子性
 * @author
 */
public class Demo {
    private static Logger log = LogManager.getLogger(Demo.class);
    volatile int count = 0;
    public void test() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        List<Thread> threads = new ArrayList();
        //new 10個线程
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(demo::test, "t-" + i));
        }
        //遍历这个10个线程  依次启动
        threads.forEach((o) -> o.start());
        //等待10个线程执行完
        threads.forEach((o) -> {
            try {
                o.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        log.debug(demo.count + "");
    }
}
