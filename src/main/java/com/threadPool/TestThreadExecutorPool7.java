package com.shadow.threadPool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author 钢牌讲师--子路
 * shutdown
 **/
@Slf4j(topic = "e")
public class TestThreadExecutorPool7 {
    static ThreadPoolExecutor threadPoolExecutor;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //最多2个thread  5个 task  其中2个task会在队列当中 1个task在put的时候会阻塞
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,2,1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(2));

        //t1
        threadPoolExecutor.execute(()->{
            log.debug("task1--start----");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("task1--END----");
        });

        //t2
        threadPoolExecutor.execute(()->{
            log.debug("task2--start----");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("task2--END----");
        });


//        threadPoolExecutor.execute(()->{
//            log.debug("task3--start----");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            log.debug("task3--END----");
//        });

        log.debug("shutdown");
        threadPoolExecutor.shutdown();
        //等待线程池成为终结状态执行---超时---提前  为了线程池终结之后做一些善后工作
        threadPoolExecutor.awaitTermination(3,TimeUnit.SECONDS);
        log.debug("main  end");


    }

    private static void shutdownNow() {
        //最多2个thread  5个 task  其中2个task会在队列当中 1个task在put的时候会阻塞
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,2,1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(2));

        //t1
        threadPoolExecutor.execute(()->{
            log.debug("task1--start----");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("task1--END----");
        });

        //t2
        threadPoolExecutor.execute(()->{
            log.debug("task2--start----");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("task2--END----");
        });

        //t1 || t2  队列当中
        threadPoolExecutor.execute(()->{
            log.debug("task3--start----");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("task3--END----");
        });


        //t1 || t2  队列当中
        threadPoolExecutor.execute(()->{
            log.debug("task4--start----");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("task4--END----");
        });

        log.debug("shutdown");
        //返回的是队列当中排队的任务
        List<Runnable> runnables = threadPoolExecutor.shutdownNow();
        log.debug("queue size:[{}]",runnables.size());
    }

    private static void shutdown() throws InterruptedException {
        //最多2个thread  5个 task  其中2个task会在队列当中 1个task在put的时候会阻塞
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,2,1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1));
        MyTask myTask = new MyTask(1);

        //t1
        threadPoolExecutor.execute(myTask);


        //t2
        threadPoolExecutor.execute(()->{
            log.debug("task2--start----");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("task2--END----");
        });

        //t1 || t2  队列当中
        threadPoolExecutor.execute(()->{
            log.debug("task3--start----");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("task3--END----");
        });

        log.debug("shutdown");
        threadPoolExecutor.shutdown();


        //t1 || t2  队列当中
        threadPoolExecutor.execute(()->{
            log.debug("task4--start----");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("task4--END----");
        });

    }


    static class MyTask implements Runnable {
        private int taskNum;

        public int getTaskNum() {
            return taskNum;
        }

        public MyTask(int num) {
            this.taskNum = num;
        }

        @Override
        public void run() {
            log.debug("task1--start----");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("task1--end----");
        }
    }
}
