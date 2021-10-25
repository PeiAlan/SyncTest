package com.example.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 重载实现
 *
 * @author Ellison_Pei
 * @date 2021/10/22 09:19
 * @since 1.0
 **/
@Slf4j
public class OverloadTest {
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
        OverloadTest test = new OverloadTest();
        log.debug("1 + 2 = {}", test.add(1, 2));
        log.debug("\"{}\" 拼接 \"{}\" = {}", "忍者", "必须死", test.add("忍者", "必须死"));
        log.debug("true & false = {}", test.add(true, false));
    }
}
