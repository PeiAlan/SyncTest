package com.example.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.Condition;

public class CustomSync extends AbstractQueuedLongSynchronizer {


    @Override
    public boolean tryAcquire(long acquires) {
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }


    @Override
    protected boolean tryRelease(long arg) {

        if(getState() == 0) {
            throw new IllegalMonitorStateException();
        }
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }

    @Override
    protected boolean isHeldExclusively() {
        return getState()==1;
    }


    public Condition newCondition() {
        return new ConditionObject();
    }
}
