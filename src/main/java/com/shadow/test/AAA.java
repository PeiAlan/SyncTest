package com.shadow.test;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>TODO</p>
 *
 * @author Ellison Pei
 * @date 2020/9/10 23:20
 **/
@Slf4j(topic = "ellison")
public class AAA implements AutoCloseable {
    protected Object lock;

    @Override
    public void close() throws Exception {
        synchronized (lock){
            log.debug("调用了close方法");
        }
    }
}
