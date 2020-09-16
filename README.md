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
