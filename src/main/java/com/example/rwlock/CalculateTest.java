package com.example.rwlock;

/**
 * Java 逻辑运算 详解：
 * https://blog.csdn.net/zhou_fan_xi/article/details/84375385
 *
 * @author Ellison_Pei
 * @date 2021/11/25 10:19
 * @since 1.0
 **/
public class CalculateTest {

    private static final int LG_READERS = 7;

    // Values for lock state and stamp operations
    private static final long RUNIT = 1L;
    private static final long WBIT  = 1L << LG_READERS;
    private static final long RBITS = WBIT - 1L;
    private static final long RFULL = RBITS - 1L;
    private static final long ABITS = RBITS | WBIT;
    private static final long SBITS = ~RBITS; // note overlap with ABITS


    static final int SHARED_SHIFT = 16;
    static final int SHARED_UNIT = (1 << SHARED_SHIFT);
    static final int MAX_COUNT = (1 << SHARED_SHIFT) - 1;
    // 65535
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

    static int sharedCount(int c) {
        return c >>> SHARED_SHIFT;
    }

    static int exclusiveCount(int c) {
        return c & EXCLUSIVE_MASK;
    }

    public static void main(String[] args) {
        int i = 6;
        System.out.println(exclusiveCount(i));

        System.out.println("12 & 5 = " + (12 & 5));
        System.out.println("127 | 128 = " + (127 | 128));
        System.out.println("~127 = " + ~127);
        System.out.println(~130);
    }
}
