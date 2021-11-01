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

### 2、修饰类中的普通方法，但是锁的是不同的对象


###  3、修饰类中static修饰的方法


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

### 5、锁对象的改变


### 6、脏读问题

### 7、Synchronized 的可重入

### 8、synchronized 和异常的关系


### 9、volatile 关键字


### 10、volatile 关键字并不能保证原子性

对volatile 修饰的变量进行单词操作时，具有原子性。但进行类似++这种复合操作时，就不具有原子性了


### 11、synchronized既保证了原子性又保证了可见性


## 二、Synchronized 的底层原理

​		Synchronized 在 JVM 里的实现都是基于进入和退出 Monitor 对象来实现方法同步和代码块同步，虽然具体实现细节不一样，但是都可以通过成对的 MonitorEnter 和 MonitorExit 指令来实现。 
​		对同步块，MonitorEnter 指令插入在同步代码块的开始位置，当代码执行到该指令时，将会尝试获取该对象 Monitor 的所有权，即尝试获得该对象的锁，而 monitorExit 指令则插入在方法结束处和异常处，JVM 保证每个 MonitorEnter 必须有对应的 MonitorExit。 
​		对同步方法，从同步方法反编译的结果来看，方法的同步并没有通过指令 monitorenter和 monitorexit 来实现，相对于普通方法，其常量池中多了 ACC_SYNCHRONIZED 标示符。 
​		JVM 就是根据该标示符来实现方法的同步的：当方法被调用时，调用指令将会检查方法的 ACC_SYNCHRONIZED 访问标志是否被设置，如果设置了，执行线程将先获取 monitor，获取成功之后才能执行方法体，方法执行完后再释放 monitor。在方法执行期间，其他任何线程都无法再获得同一个 monitor 对象。 
​		synchronized 使用的锁是存放在 Java 对象头里面，具体位置是对象头里面的 MarkWord， MarkWord 里默认数据是存储对象的 HashCode 等信息，但是会随着对象的运行改变而发生变化，不同的锁状态对应着不同的记录存储方式。在具体优化上，从 1.6 开始引入了偏向锁、 自旋锁等机制提升性能。 

# AQS的实现和Lock的使用及原理
# 锁的使用以及AQS的原理和使用

## 锁的原理与使用

要想知道锁的使用就要先了解线程同步的模式，线程同步是一种保护性暂停模式。

### 保护性暂停模式

#### 定义（**Guarded Suspension Design Pattern**）

1. 某个结果需要在多线程之间传递，则可以让这些线程关联到一个对象 GuardedObject
2. 但是如果这个结果需要不断的从一个线程到另一个线程那么可以使用消息队列（见生产者/消费者）
3. 我们前面前面说的join、future采用的就是这个模式

#### 如何实现

##### 分析1

这种做法的问题在于如果主线程被别人叫醒了；就会立马返回；比如超时时间是5s；但是在第2s的时候别人把主线程叫醒了，那么主线程会立马返回没有等足5s

所以需要设计一个经历时间；也就是从他wait到被别人叫醒中间一共经历了多少时间；判断这个时间是否符合超时;如果要计算这个经历时间必须知道开始时间和结束时间；

1、首先定一个开始时间等于当前时间 long begin = System.currentTimeMillis();

2、定一个经历时间 默认为0 long timePassed = 0;

3、判断是否满足条件，满足则返回结果不阻塞；不满足则然后进入while循环  首先计算等待时间（也就是wait的时间）  millis-timePassed 

4、判断等待时间是否小于0；小于0标识超时了直接结束while循环 返回不等待了

5、如果大于0 进入wait 这样就算提前被别人叫醒 也会在继续wait


## 死锁
如果线程需要获取多把锁那么就很可能会发现死锁


## 活锁  
不可避免   但是我可以错开他们的执行时间

## Lock--应用

特点：

1. 可打断，可重入
2. 可以设置超时时间
3. 可以设置为公平锁
4. 支持多个条件变量
5. 支持读写锁(单独的篇章来讲)

### 基本语法
### 重入
### 可打断
### 超时
### 多個條件
synchronized 中也有条件变量，就是以前讲的waitSet 不满足条件的线程进入waitSet；而Lock也有waitSet而且有多个
### 读写锁

读读并发

读写互斥

写写互斥

读写锁读锁不支持条件

读写锁使用的例子



读写支持重入但是只支持降级不支持升级


## AQS框架

### 定义

1、全称是 AbstractQueuedSynchronizer

2、阻塞式锁和相关的同步器工具的框架；

3、AQS用一个变量（volatile state） 属性来表示锁的状态，子类去维护这个状态

3、getState、compareAndSetState   cas改变这个变量

4、独占模式是只有一个线程能够访问资源

5、而共享模式可以允许多个线程访问资源（读写锁）

6、内部维护了一个FIFO等待队列，类似于 synchronized关键字当中的  Monitor 的 EntryList

7、条件变量来实现等待、唤醒机制，支持多个条件变量，类似于 Monitor 的 WaitSet

8、内部维护了一个Thread exclusiveOwnerThread 来记录当前持有锁的那个线程



### 功能

1、实现阻塞获取锁 acquire  拿不到锁就去阻塞 等待锁被释放再次获取锁

2、实现非阻塞尝试获取锁 tryAcquire 拿不到锁则直接放弃

3、实现获取锁超时机制 

4、实现通过打断来取消

5、实现独占锁及共享锁

6、实现条件不满足的时候等待



### 自定义实现AQS框架

#### 继承AQS 实现其主要方法


#### 实现Lock接口实现加锁解锁


# ReentrantLock的使用及原理

见博客：[2020-09-20-ReentrantLock的使用及原理](https://ellisonpei.gitee.io/2020/09/20/2020-09-20-reentrantlock-de-shi-yong-ji-yuan-li/)