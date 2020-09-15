# Synchronized关键字的使用以及基本原理

## 一、Synchronized的使用场景

### 首先要清楚synchronized的使用原理

```tex
1. 作用于方法时，锁住的是对象的实例(this)； 
2. 当作用于静态方法时，锁住的是Class实例，又因为Class的相关数据存储在永久带PermGen（jdk1.8 则 metaspace），永久带是全局共享的，因此静态方法锁相当于类的一个全局锁，会锁所有调用该方法的线程； 
3. synchronized 作用于一个对象实例时，锁住的是所有以该对象为锁的代码块。 它有多个队列，当多个线程一起访问某个对象监视器的时候，对象监视器会将这些线程存储在不同的容器中。
```

编码测试，使用场景模拟如下

### 1、修饰类中的普通方法

```java
//1、修饰类中的普通方法，锁的就是实例对象（this）
@Slf4j(topic = "ellison")
public class BasicLock {
    /**
     * 修饰方法，锁的是实例对象
     */
    public synchronized void x(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("x");
    }
    /**
     * 修饰方法，锁的是实例对象
     */
    public synchronized void y(){
        log.debug("y");
    }
    public void z(){
        log.debug("z");
    }
  /**
   * 下面测试方法有两种输出结果：
   * 1、等1s  打印x  打印y
   * 2、先打印y  等1s  x
   */
    public static void main(String[] args) {
        BasicLock basicLock = new BasicLock();
        new Thread(()->{
            log.debug("start");
            basicLock.x();
        },"t1").start();
        new Thread(()->{
            log.debug("start");
            basicLock.y();
        },"t2").start();
    }
}
```

### 2、修饰类中的普通方法，但是锁的是不同的对象

```java
// 2、将x方法添加static关键字
@Slf4j(topic = "ellison")
public class BasicLock1 {
    /**
     * 修饰static方法，锁的是当前的类对象
     */
    public synchronized static void x(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("x");
    }
    /**
     * 修饰方法，锁的是实例对象
     */
    public synchronized void y(){
        log.debug("y");
    }
    public void z(){
        log.debug("z");
    }
   /**
     * 执行结果永远是y在前
     * 因为 锁的是不同的对象，x睡眠了1秒，所以一定是y先打印
     * @param args
     */
    public static void main(String[] args) {
        BasicLock basicLock = new BasicLock();
        BasicLock basicLock1 = new BasicLock();
        new Thread(()->{
            log.debug("start");
            basicLock.x();
        },"t1").start();

        new Thread(()->{
            log.debug("start");
            basicLock1.y();
        },"t2").start();
    }
  
}
```

###  3、修饰类中static修饰的方法

```java
//3、x方法改为 static 修饰，修饰类中static修饰的方法时锁的是当前类的class
@Slf4j(topic = "ellison")
public class BasicLock1 {
    /**
     * 修饰static方法，锁的是当前的类 BasicLock1.class
     */
    public synchronized static void x(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("x");
    }
    /**
     * 修饰方法，锁的是实例对象
     */
    public synchronized void y(){
        log.debug("y");
    }
    public void z(){
        log.debug("z");
    }
  
    /**
     * t1、t2启动时 ，线程打印是存在竞争关系的
     * 然而在在执行 x  y  方法时，x  sleep了1秒，所有时y先行打印
     * 锁的对象不一样
     * @param args
     */
    public static void main(String[] args) {
        BasicLock1 basicLock1 = new BasicLock1();
        // x 锁的是BasicLock1 这个类的class
        new Thread(()->{
            log.debug("t1--start");
            basicLock1.x();
        },"t1").start();

        /**
         * y 锁的是new BasicLock1() 对象
         */
        new Thread(()->{
            log.debug("t2--start");
            basicLock1.y();
        },"t2").start();
    }
  
    
}
```

### 方法之间的性能测试

测试方法的性能，这里提供一个测试工具 JMH  ，关于JMH的使用。

推荐博客   https://www.zhihu.com/question/276455629/answer/1259967560

```java
// 接下来的情景就是  x  y 方法都被synchronized static 修饰，然后测试每个方式的性能
//本人做的测试过程中是通过mvn 命令构建的JMH项目
//具体命令如下
  mvn archetype:generate -DinteractiveMode=false -DarchetypeGroupId=org.openjdk.jmh -DarchetypeArtifactId=jmh-java-benchmark-archetype -DgroupId=com.ellison.jmh -DartifactId=pei -Dversion=1.0.0-SNAPSHOT -DarchetypeCatalog=local
    
//命令中的 -DarchetypeCatalog=local 如果能从外网中下载到相应文件的话可以不需要，我这里是我翻墙提前下载好的xml文件，所以加了local
```

###      4、synchronized 关键字的线程安全问题

```java
// 情景一
@Slf4j(topic = "ellison")
public class Demo1 {
    private int count = 10;
    private Object object = new Object();
    public void test(){
        synchronized (object){
            count--;
            log.debug(Thread.currentThread().getName() + " count = " + count);
        }
    }
    /**
     * synchronized关键字
     * synchronized关键字锁定的是对象不是代码块,demo中锁的是object对象的实例
     * 锁定的对象有两种:1.类的实例 2.类对象(类锁)
     * 加synchronized关键字之后不一定能实现线程安全，具体还要看锁定的对象是否唯一。
     */
    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        new Thread(demo1::test, "t1").start();

        //加锁的不是同一个对象，线程不安全
        //Demo1 demo11 = new Demo1();
        //new Thread(demo11::test, "t2").start();
    }
}
// 情景二
@Slf4j(topic = "ellison")
public class Demo2 {
    private int count = 10;
    public  void test(){
        //synchronized(this)锁定的是当前类的实例,这里锁定的是Demo2类的实例
        synchronized (this){
            count--;
            log.debug(Thread.currentThread().getName() + " count = " + count);
        }
    }
    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        new Thread(demo2::test,"t1").start();
        
        //synchronized(this)锁定的是当前类的实例
        //实例不同  锁的对象就不同
        Demo2 demo21 = new Demo2();
        new Thread(demo21::test, "t2").start();
    }
}

// 情景三
@Slf4j(topic = "ellison")
public class Demo3 {
    private int count = 10;
    //直接加在方法声明上，相当于是synchronized(this)
    public synchronized void test(){
        count--;
        log.debug(Thread.currentThread().getName() + " count = " + count);
    }
    public static void main(String[] args) {
        Demo3 demo3 = new Demo3();
        new Thread(demo3::test,"t1").start();

        //这时  线程是安全的
//        new Thread(demo3::test,"t2").start();
        
        //此时又是线程不安全的，因为锁的对象变了
        Demo3 demo31 = new Demo3();
        new Thread(demo31::test, "t3").start();
    }

}

// 情景四
@Slf4j(topic = "ellison")
public class Demo4 {
    private static int count = 10;
    //synchronize关键字修饰静态方法锁定的是类的对象
    public synchronized static void test(){
        count--;
        log.debug(Thread.currentThread().getName() + " count = " + count);
    }
    public static void test2(){
        synchronized (Demo4.class){//这里不能替换成this
            count--;
        }
    }
}
```

### 5、锁对象的改变

```java
/**
 * 锁对象的改变
 * 锁定某对象o，如果o的属性发生改变，不影响锁的使用
 * 但是如果o变成另外一个对象，则锁定的对象发生改变
 * 应该避免将锁定对象的引用变成另外一个对象
 */
@Slf4j(topic = "enjoy")
public class Demo1 {
    Object o = new Object();
    public void test(){
        synchronized (o) {
            //t1 在这里无线执行
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        Demo1 demo = new Demo1();
        new Thread(demo :: test, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(demo :: test, "t2");
        demo.o = new Object();
        //t2能否执行？
        t2.start();
    }

}

/**
 *不要以字符串常量作为锁定的对象
 *
 */
/**
 * 同步代码快中的语句越少越好
 * 比较test1和test2
 * 业务逻辑中只有count++这句需要sync，这时不应该给整个方法上锁
 * 采用细粒度的锁，可以使线程争用时间变短，从而提高效率
 */
@Slf4j(topic = "ellison")
public class Demo3 {
    int count = 0;
    public synchronized void test1(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        count ++;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 局部加锁
     */
    public void test2(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            count ++;
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```







## 二、Synchronized 的底层原理






