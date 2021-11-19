package com.example.aqsTest;

/**
 * @author
 * 同步执行
 */
public class CustomLockTest {

    private static  int sum = 0;
    private static CustomLock lock = new CustomLock();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(()->{
                //加锁
                lock.lock();
                try {
                    // 临界区代码
                    // TODO 业务逻辑：读写操作不能保证线程安全
                    for (int j = 0; j < 10000; j++) {
                        sum++;
                    }
                } finally {
                    // 解锁
                    lock.unlock();
                }
            });
            thread.start();
        }

        Thread.sleep(2000);
        System.out.println(sum);
    }
}
