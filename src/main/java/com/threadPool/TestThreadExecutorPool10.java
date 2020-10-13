package com.shadow.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author 钢牌讲师--子路
 * 每周的周三 22点0分0秒去执行一个任务 同步redis mysql 只有一个线程
 **/
@Slf4j(topic = "e")
public class TestThreadExecutorPool10 {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //一个线程
        ScheduledThreadPoolExecutor scheduled
                = new ScheduledThreadPoolExecutor(1);

        /**
         * 1、当前在编码的时候距离周三还有多久------initialDelay
         *     当前时间   周三的时间
         * 2、下一次多久之后执行（周期时间）delay
         * 3、单位
         */
        //当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        //周三的时间
        LocalDateTime targetTime = currentTime.withHour(21).withMinute(2).withSecond(0).withNano(0).with(DayOfWeek.TUESDAY);

        if(targetTime.compareTo(currentTime)<0){
            targetTime = targetTime.plusWeeks(1);

        }

        long l = Duration.between(currentTime, targetTime).toMillis();
        long delay = 1000 * 60 *60 *24 *7;
        log.debug("当前时间：[{}]",l);
        log.debug("目标时间：[{}]",targetTime);

        scheduled.scheduleWithFixedDelay(()->{
            log.debug("redis---mysql async");
        },l,delay,TimeUnit.MILLISECONDS);




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
            log.debug(getTaskNum()+"");
        }
    }
}
