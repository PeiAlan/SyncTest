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



### 6、脏读问题

```java
/**
 * 脏读问题
 * 实际业务当中应该看是否允许脏读，
 * 不允许的情况下对读方法也要加锁
 */
@Slf4j(topic = "enjoy")
public class Demo {

    //卡的持有人
    String name;

    //卡上的余额
    double balance;

    /**
     *
     * @param name
     * @param balance
     */
    public synchronized void set(String name,double balance){
        this.name = name;
        try {
            //模拟存钱耗时
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public synchronized double getBalance(String name){
        return this.balance;
    }

    public static void main(String[] args) {
        Demo demo = new Demo();

        //2s
        new Thread(()->demo.set("zl",100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //1s之后 结果 0
        log.debug(demo.getBalance("zl")+"");//

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //3s之后就算100
        log.debug(demo.getBalance("zl")+"");
    }

}
```

### 7、Synchronized 的可重入

```java
//一个同步方法调用另外一个同步方法，能否得到锁?
//重入  synchronized默认支持重入
@Slf4j(topic = "enjoy")
public class Demo {

    synchronized void test1(){
        log.debug("test1 start.........");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test2();
    }

    /**
     * 为什么test2还需要加sync
     *
     * 他本身就包含在test1 而test1已经加了sync
     *
     * 为了防止其他线程在不执行test1的时候直接执行test2 而造成的线程不安全
     */
    synchronized void test2(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("test2 start.......");
    }
    public static void main(String[] args) {
        Demo demo= new Demo();
        demo.test1();
    }

}

// 情景二

//这里是重入锁的另外一种情况，继承
@Slf4j(topic = "enjoy")
public class Demo {
    synchronized void test(){
        log.debug("demo test start........");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("demo test end........");
    }
    public static void main(String[] args) {
            new Demo2().test();
    }

}
@Slf4j(topic = "enjoy")
class Demo2 extends Demo {
    @Override
    synchronized void test(){
        log.debug("demo2 test start........");
        super.test();
        log.debug("demo2 test end........");
    }
}
```

### 8、synchronized 和异常的关系

```java
/**
 * synchronized 和异常的关系
 * T2线程能否执行？
 */
@Slf4j(topic = "enjoy")
public class Demo {
    Object o = new Object();

    int count = 0;

     void test(){
         synchronized(o) {
             //t1进入并且启动
             log.debug(Thread.currentThread().getName() + " start......");
             //t1 会死循环 t1 讲道理不会释放锁
             while (true) {
                 count++;
                 log.debug(Thread.currentThread().getName() + " count = " + count);
                 try {
                     TimeUnit.SECONDS.sleep(1);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 //加5次之后 发生异常
                 /**
                  * 如果程序发生异常如果没有try 则会释放锁
                  * 反之不会释放锁
                  */
                 if (count == 5) {
                     int i = 1 / 0;
                 }
             }
         }
    }

    public static void main(String[] args) {
        Demo demo11 = new Demo();
       // Runnable r = () -> demo11.test();
       // new Thread(r, "t1").start();

        new Thread(()->{
            demo11.test();
        },"t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            demo11.test();
        }, "t2").start();
    }

}
```

### 9、volatile 关键字

```java
/**
 * volatile 关键字，使一个变量在多个线程间可见
 * mian,t1线程都用到一个变量，java默认是T1线程中保留一份副本，这样如果main线程修改了该变量，
 * t1线程未必知道
 *
 * 使用volatile关键字，会让所有线程都会读到变量的修改值
 *
 * 在下面的代码中，running是存在于堆内存的t对象中
 * 当线程t1开始运行的时候，会把running值从内存中读到t1线程的工作区，在运行过程中直接使用这个副本，
 * 并不会每次都去读取堆内存，这样，当主线程修改running的值之后，t1线程感知不到，所以不会停止运行
 *
 *
 * 但是这可能是个错误
 */
@Slf4j(topic = "enjoy")
public class Demo {
    boolean running = true;
    List<String> list = new ArrayList<>();
    
    /**
     * t1线程
     */
    public void test(){
        log.debug("test start...");
        boolean flag =running;
            while (running){

            }
        log.debug("test end...");
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        new Thread(demo :: test,"t1").start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        demo.running = false;
    }

}
```

### 10、volatile 关键字并不能保证原子性

对volatile 修饰的变量进行单词操作时，具有原子性。但进行类似++这种复合操作时，就不具有原子性了

```java
/**
 * 比如说第一个线程加到100了，还没往上加，另外一个线程来了，把100拿过来执行方法，
 * 然后第一个线程继续加到101，第二个线程也加到101，他两往回写都是101，线程不会管你加到哪儿了，
 * 虽然说加了2但是实际上只加了1.
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，
 * 也就是说volatile不能替代synchronized或者说volatile保证不了原子性
 */
@Slf4j(topic = "enjoy")
public class Demo {
    volatile int count = 0;
    public void test(){
        for (int i = 0; i < 10000; i++) {
            count ++;
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        List<Thread> threads = new ArrayList();
        //new 10個线程
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(demo::test, "t-" + i));
        }
        //遍历这个10个线程  依次启动
        threads.forEach((o)->o.start());
        //等待10个线程执行完
        threads.forEach((o)->{
            try {
                o.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        log.debug(demo.count+"");
    }

}
```

### 11、synchronized既保证了原子性又保证了可见性

```java
@Slf4j(topic = "enjoy")
public class Demo {
    int count = 0;
    //相比较上一个例子，synchronized既保证了原子性又保证了可见性
    public synchronized void test(){
        for (int i = 0; i < 10000; i++) {
            count ++;
        }
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(demo::test, "thread-" + i));
        }
        threads.forEach((o)->o.start());
        threads.forEach((o)->{
            try {
                o.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
       log.debug(demo.count+"");
    }

}
```



## 二、Synchronized 的底层原理

​		Synchronized 在 JVM 里的实现都是基于进入和退出 Monitor 对象来实现方法同步和代码块同步，虽然具体实现细节不一样，但是都可以通过成对的 MonitorEnter 和 MonitorExit 指令来实现。 
​		对同步块，MonitorEnter 指令插入在同步代码块的开始位置，当代码执行到该指令时，将会尝试获取该对象 Monitor 的所有权，即尝试获得该对象的锁，而 monitorExit 指令则插入在方法结束处和异常处，JVM 保证每个 MonitorEnter 必须有对应的 MonitorExit。 
​		对同步方法，从同步方法反编译的结果来看，方法的同步并没有通过指令 monitorenter和 monitorexit 来实现，相对于普通方法，其常量池中多了 ACC_SYNCHRONIZED 标示符。 
​		JVM 就是根据该标示符来实现方法的同步的：当方法被调用时，调用指令将会检查方法的 ACC_SYNCHRONIZED 访问标志是否被设置，如果设置了，执行线程将先获取 monitor，获取成功之后才能执行方法体，方法执行完后再释放 monitor。在方法执行期间，其他任何线程都无法再获得同一个 monitor 对象。 
​		synchronized 使用的锁是存放在 Java 对象头里面，具体位置是对象头里面的 MarkWord， MarkWord 里默认数据是存储对象的 HashCode 等信息，但是会随着对象的运行改变而发生变化，不同的锁状态对应着不同的记录存储方式。在具体优化上，从 1.6 开始引入了偏向锁、 自旋锁等机制提升性能。 


