package com.example.blockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 传递性任务队列
 * @author
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
      //新建一个SynchronousQueue同步队列
      final BlockingQueue<Integer> synchronousQueue = new SynchronousQueue<>(false);

      //启动一个生产者线程插入对象
      SynchronousQueueProducer queueProducer = new SynchronousQueueProducer(synchronousQueue);
      new Thread(queueProducer, "t0").start();

      //启动两个消费者线程移除对象
      SynchronousQueueConsumer queueConsumer1 = new SynchronousQueueConsumer(synchronousQueue);
      new Thread(queueConsumer1, "t1").start();
    
      SynchronousQueueConsumer queueConsumer2 = new SynchronousQueueConsumer(synchronousQueue);
      new Thread(queueConsumer2, "t2").start();
    }
}
