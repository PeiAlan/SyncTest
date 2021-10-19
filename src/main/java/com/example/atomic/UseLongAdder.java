package com.example.atomic;

import java.util.concurrent.atomic.LongAdder;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/10/7 14:22
 * @since 1.0
 **/
public class UseLongAdder {
    static LongAdder id = new LongAdder();

    public static void main(String[] args) {
        id.longValue();
        id.increment();
        id.longValue();
        id.add(3L);
        id.longValue();
    }


}
