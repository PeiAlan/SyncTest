package com.example.safeSingleton;

/**
 * 懒汉式-双重检查
 *
 * 线程不一定安全  ，加 volatile 禁止指令重拍
 */
public class SingleDoubleCheck {
    private volatile static SingleDoubleCheck singleDcl;
    /** 私有化 */
    private SingleDoubleCheck(){}

    public static SingleDoubleCheck getInstance(){
        //第一次检查，不加锁
        if (singleDcl == null){
            System.out.println(Thread.currentThread()+" is null");
            //加锁
            synchronized(SingleDoubleCheck.class){
                //第二次检查，加锁情况下
                if (singleDcl == null){
                    System.out.println(Thread.currentThread()+" is null");
                    //内存中分配空间  1
                    //空间初始化 2
                    //把这个空间的地址给我们的引用  3
                    singleDcl = new SingleDoubleCheck();
                }
            }
        }
        return singleDcl;
    }
}
