package com.poolv2;

/**
 * @Author 钢牌讲师--子路
 **/
public class A implements PolicyHandler {
    @Override
    public void handler(EnjoyQueue queue, CustomTask task) {
        throw new RuntimeException("线程池不支持这么多队列");

    }
}
