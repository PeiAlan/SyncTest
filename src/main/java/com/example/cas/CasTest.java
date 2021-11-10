package com.example.cas;

import com.example.utils.UnsafeUtil;
import sun.misc.Unsafe;


/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/11/8 15:01
 * @since 1.0
 **/
public class CasTest {
    private static Unsafe UNSAFE;

    public static void main(String[] args) throws NoSuchFieldException {
        UNSAFE = UnsafeUtil.getUnsafe();
        Entity entity = new Entity();
        long offset = UnsafeUtil.getFieldOffset(UNSAFE, entity.getClass(), "x");
        boolean successful;


    }
}


class Entity{
    private int x;
}
