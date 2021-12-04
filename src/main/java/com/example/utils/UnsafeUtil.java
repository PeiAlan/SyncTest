package com.example.utils;

import org.springframework.objenesis.ObjenesisException;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/11/8 16:10
 * @since 1.0
 **/
public class UnsafeUtil {
    private static final Unsafe unsafe;

    private UnsafeUtil() {
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }
    /**
     * 获取字段的内存偏移量
     *
     * @param unsafe
     * @param clazz
     * @param fieldName
     * @return
     */
    public static long getFieldOffset(Unsafe unsafe, Class clazz, String fieldName) {
        try {
            return unsafe.objectFieldOffset(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    static {
        Field f;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException var3) {
            throw new ObjenesisException(var3);
        }

        f.setAccessible(true);

        try {
            unsafe = (Unsafe)f.get((Object)null);
        } catch (IllegalAccessException var2) {
            throw new ObjenesisException(var2);
        }
    }
}
