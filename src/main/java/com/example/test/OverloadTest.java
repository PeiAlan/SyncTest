package com.example.test;

import com.example.guarded.Test;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/10/22 09:19
 * @since 1.0
 **/
@Slf4j
public class Test1 {
    private Integer add(int a, int b) {
        return a + b;
    }

    private String add(String a, String b) {
        return a + b;
    }

    private boolean add(boolean a, boolean b) {
        return a & b;
    }

    public static void main(String[] args) {
        Test1 test1 = new Test1();
        log.debug("1 + 2 = {}", test1.add(1, 2));
        log.debug("\"{}\" 拼接 \"{}\" = {}", "忍者", "必须死", test1.add("忍者", "必须死"));
        log.debug("true & false = {}", test1.add(true, false));
    }
}
