package com.example.lockTest;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 *  Hotspot虚拟机中，对象在内存中存储的布局可以分为三块区域：对象头（Header）、实例数据（Instance Data）和对齐填充（Padding）。
 *      - 对象头：比如 hash码，对象所属的年代，对象锁，锁状态标志，偏向锁（线程）ID，偏向时间，数组长度（数组对象才有）等。
 *      - 实例数据：存放类的属性数据信息，包括父类的属性信息；
 *      - 对齐填充：由于虚拟机要求 对象起始地址必须是8字节的整数倍。填充数据不是必须存在的，仅仅是为了字节对齐。
 *
 *
 * 关闭指针压缩（-XX:-UseCompressedOops）
 *
 *  //关闭延迟开启偏向锁
 * -XX:BiasedLockingStartupDelay=0
 * //禁止偏向锁
 * -XX:-UseBiasedLocking
 * //启用偏向锁
 * -XX:+UseBiasedLocking
 *
 * @author Ellison Pei
 */
public class ObjectTest {

    public static void main(String[] args) throws InterruptedException {
        //jvm延迟偏向
        Thread.sleep(5000);
        Object obj = new Test();
        //Object obj = new Integer[4];
        //obj.hashCode();
        // 查看对象内部信息
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        new Thread(()->{
            synchronized (obj){
                System.out.println(Thread.currentThread().getName()+"\n"+ClassLayout.parseInstance(obj).toPrintable());
            }
            System.out.println(Thread.currentThread().getName()+"释放锁\n"+ClassLayout.parseInstance(obj).toPrintable());

            // sleep方法对线程1进行保活。 可能是 jvm 优化
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"Thread1").start();


        Thread.sleep(2000);


        new Thread(()->{
            synchronized (obj){
                System.out.println(Thread.currentThread().getName()+"\n"+ClassLayout.parseInstance(obj).toPrintable());
            }
        },"Thread2").start();

    }
}

class Test{
    private boolean flag;
    private long  p;
}
