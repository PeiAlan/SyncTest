package com.aqs;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

/**
 * 同步器  工具---为锁服务
 */
public class EnjoySync extends AbstractQueuedSynchronizer {

    /**
     * 主要实现获取 不去重入
     * @param arg
     * @return
     */
    protected boolean tryAcquire(int arg) {
        if(getState()==0){
            boolean b = compareAndSetState(0, 1);
            if(b){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
        }
        return false;
    }

    /**
     * 释放锁
     * @param arg
     * @return
     */
    @Override
    protected boolean tryRelease(int arg) {
        setExclusiveOwnerThread(null);
        setState(0);

        return true;
    }

    /**
     * 有没有被人持有
     * @return
     */
    @Override
    protected boolean isHeldExclusively() {
        boolean f= getState()==0;
        if(f){
            return false;
        }else {
            return true;
        }
    }



    public Condition newCondition() {
        return new ConditionObject();
    }
}
