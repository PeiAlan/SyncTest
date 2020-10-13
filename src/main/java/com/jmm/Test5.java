package com.jmm;

/**
 * @Author 钢牌讲师--子路
 **/
public class Test5 {
    private volatile static Test5 INSTANCE;

    private Test5() {

    }


    /**
     * 有线程安全问题的
     *
     * 很高的并发-----指令重排
     * @return
     */
    public   static Test5 getInstance() {
        if (INSTANCE == null) {
            synchronized (Test5.class){// 原子性 会发生线程切换
                if(INSTANCE == null){
                    //指令重排
                    /**
                     * 分配内存
                     *
                     * 地址赋值INSTANCE
                     *
                     * 初始化
                     */
                    INSTANCE = new Test5();
                }
            }
        }
        return INSTANCE;
    }
}