package com.example.boxing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/10/11 14:48
 * @since 1.0
 **/
@Slf4j
public class IntegerBoxingTest {
    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start("使用 Integer");
        wrong();
        watch.stop();
        watch.start("使用 int");
        right();
        watch.stop();
        log.info(watch.prettyPrint());
    }

    private static void wrong(){
        Integer sum = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        log.info(sum + "");
    }

    private static void right(){
        int sum = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        log.info(sum + "");
    }
}
