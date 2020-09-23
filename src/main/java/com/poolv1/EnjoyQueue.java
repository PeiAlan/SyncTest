package com.poolv1;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class EnjoyQueue {

    Lock lock = new ReentrantLock();


    //如果deque当中满了的条件队列
    Condition busyws = lock.newCondition();

    //当deque当中没有了task则进入条件队列
    Condition emptyws = lock.newCondition();

    Deque<CustomTask> deque = new ArrayDeque();

    //队列的元素上限
    private int queueSize;


    public EnjoyQueue(int queueSize) {
        this.queueSize = queueSize;
    }



    public void put(CustomTask task){
        lock.lock();
        try{
            /**
             * step1 判断队列是否达到上限
             */
            while (deque.size()==queueSize){
                log.debug("put 队列已经满了 应该去阻塞");
                try {
                    busyws.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("put 队列可以存放 则put一个task[{}]",task.getName());
            deque.addLast(task);
            emptyws.signal();
        }finally {
            lock.unlock();
        }


    }

    public CustomTask poll(){
        lock.lock();
        try{
            log.debug("poll 一个task");
            /**
             * 要去判断队列当中是否有task
             */
            while (deque.isEmpty()) {
                log.debug("poll 队列当中没有则阻塞");
                emptyws.await();
            }
            //拿出第一个个然后remove
            CustomTask task = deque.removeFirst();
            log.debug("poll 能够获取 一个正常的task[{}]",task.getName());
            busyws.signal();
            return task;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
}
