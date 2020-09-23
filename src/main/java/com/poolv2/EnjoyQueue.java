package com.poolv2;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
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


    //put 等待的超时时间

    long timeOut=2000;


    //当队列满了之后的策略
    PolicyHandler handler;


    public EnjoyQueue(int queueSize, PolicyHandler handler) {
        this.queueSize = queueSize;
        this.handler=handler;
    }



    public void put(CustomTask task){
        lock.lock();
        try{
            /**
             * step1 判断队列是否达到上限
             */
            while (deque.size()==queueSize){

                //如果阻塞超过一定时间则放弃这个任务
                log.debug("put 队列已经满了 应该去阻塞");
                try {
                    // if 超过1s 还不能put则 放弃 returen
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


    /**
     * 只能满足一种情况  如果队列满了等待超时则放弃
     * 1、队列满了之后直接放弃
     * 2、队列满了之后永远等
     * 3、队列满了之后异常
     * 4、调用者自己执行
     * 5.。。
     * 6.。。
     * @param task
     */
    public void tryput(CustomTask task){
        lock.lock();
        try{
            while (deque.size()==queueSize){
                log.debug("tryput 队列已经满taks[{}]",task.getName());
                handler.handler(this,task);
                return;
            }
            log.debug("tryput 队列可以存放 则put一个task[{}]",task.getName());
            deque.addLast(task);
            emptyws.signal();
        }catch (Exception e){
                e.printStackTrace();
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
                //如果等待时间超时 返回null
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


    public CustomTask pollTimeOut(){
        lock.lock();
        try{
            log.debug("poll 一个task");
            /**
             * 要去判断队列当中是否有task
             */
            while (deque.isEmpty()) {

                log.debug("poll 队列当中没有则阻塞");
                //如果等待时间超时 返回null  有问题
                emptyws.await(4, TimeUnit.SECONDS);
                if(deque.isEmpty()){
                    return null;
                }

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
