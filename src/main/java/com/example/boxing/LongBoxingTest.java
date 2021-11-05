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
public class LongBoxingTest {
    public static void main(String[] args) {
        StopWatch watch = new StopWatch();
        watch.start("wrong()");
        wrong();
        watch.stop();
        watch.start("right()");
        right();
        watch.stop();
        log.info(watch.prettyPrint());
    }

    private static void wrong(){
        Long sum = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        log.info(sum + "");
    }

    private static void right(){
        long sum = 0L;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        log.info(sum + "");
    }
}
