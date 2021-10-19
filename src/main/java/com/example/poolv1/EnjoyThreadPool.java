package com.example.poolv1;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class EnjoyThreadPool {

    //核心线程的集合
    HashSet<EnjoyNode> set;


    EnjoyQueue enjoyQueue;
    //核心线程数
    int core;
    public EnjoyThreadPool(int core){
        this.core=core;
        enjoyQueue = new EnjoyQueue(2);
        set = new HashSet<>();
    }

    public EnjoyQueue getEnjoyQueue() {
        return enjoyQueue;
    }


    /**
     *
     */
    public void submitTask(CustomTask target){
        /**
         * step1 应该考虑担当前线程池当中的核心线程数有么有达到上限
         */
        if(set.size()<core){
            log.debug("核心线程数还有空闲 new node");
            EnjoyNode enjoyNode = new EnjoyNode(target,"t"+(set.size()+1),this);
            log.debug("把new出来的node add到set集合当中");
            set.add(enjoyNode);
            enjoyNode.start();
            //log.debug("核心线程数还有空闲 直接执行不需要去队列当中 new t");
        }else{
            log.debug("核心线程数達到上限 应该让这个task去队列当中[{}]",target.getName());
            enjoyQueue.put(target);
        }

    }


    public void remove(EnjoyNode enjoyNode){
        set.remove(enjoyNode);
    }

}
