package com.example.blockingQueue.arrayblockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * @author Ellison Pei
 */
public class Consumer implements Runnable {

    protected BlockingQueue<Object> queue = null;

    public Consumer(BlockingQueue<Object> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println("consumer " + queue.take());
            System.out.println("consumer " + queue.take());
            System.out.println("consumer " + queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

