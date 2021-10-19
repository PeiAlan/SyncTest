package com.example.poolv1;


import lombok.extern.slf4j.Slf4j;

/**
 * @Author 钢牌讲师--子路
 **/
@Slf4j(topic = "e")
public class EnjoyNode extends Thread{
    private CustomTask target;
    private EnjoyThreadPool enjoyThreadPool;
    private EnjoyQueue enjoyQueue;

    public EnjoyNode(CustomTask target, String tname,EnjoyThreadPool enjoyThreadPool){
        setName(tname);
        this.target=target;
        this.enjoyThreadPool = enjoyThreadPool;
        enjoyQueue = enjoyThreadPool.getEnjoyQueue();

    }

    /**
     * 这里执行完成之后不能直接结束
     * 执行到这里其实有两种情况
     * 1、这个任务是直接给到我的 线程池给我的  target!=null
     * 2、这个任务从阻塞队列当中取到的 (target=enjoyQueue.poll())!=null
     */
    @Override
    public void run() {
        while (target!=null||(target=enjoyQueue.poll())!=null){
            log.debug("task---[{}]",target.getName());
            target.run();
            target=null;
        }


    }
}
