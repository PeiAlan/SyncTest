package com.example.cas;

import com.example.utils.UnsafeUtil;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;


/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/11/8 15:01
 * @since 1.0
 **/
@Slf4j
public class CasTest {
    private static Unsafe UNSAFE;

    public static void main(String[] args) throws NoSuchFieldException {
        UNSAFE = UnsafeUtil.getUnsafe();
        Entity entity = new Entity();
        long offset = UnsafeUtil.getFieldOffset(UNSAFE, Entity.class, "x");

        boolean successful;

        // 4个参数分别是：对象实例、字段的内存偏移量、字段期望值、字段新值
        successful = UNSAFE.compareAndSwapInt(entity, offset, 0, 3);
        log.debug(successful + "\t" + entity.x);

        successful = UNSAFE.compareAndSwapInt(entity, offset, 3, 5);
        log.debug(successful + "\t" + entity.x);

        successful = UNSAFE.compareAndSwapInt(entity, offset, 3, 8);
        log.debug(successful + "\t" + entity.x);


    }
}


class Entity{
    public int x;
}
