package com.testJol;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/10/12 15:55
 * @since 1.0
 **/
@Slf4j
public class TestAge {
    static Tlock l = new Tlock();
    static List<A> list = new ArrayList<>();

    @SneakyThrows
    public static void main(String[] args) {
        log.debug(ClassLayout.parseInstance(l).toPrintable());

        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(200);
                    log.debug(ClassLayout.parseInstance(l).toPrintable());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
        for (int i = 0; i < 9999; i++) {
            Thread.sleep(2);
            list.add(new A());
        }
    }
}
