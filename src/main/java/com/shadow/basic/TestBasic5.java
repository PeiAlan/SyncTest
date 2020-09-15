package com.shadow.basic;

import lombok.extern.slf4j.Slf4j;

/**
 * mvn archetype:generate -DinteractiveMode=false -DarchetypeGroupId=org.openjdk.jmh -DarchetypeArtifactId=jmh-java-benchmark-archetype -DgroupId=com.enjoy.jmh -DartifactId=zl -Dversion=1.0.0-SNAPSHOT -DarchetypeCatalog=local
 */
@Slf4j(topic = "enjoy")
public class TestBasic5 {
    public static void main(String[] args) {
        BasicLock2 basicLock2 = new BasicLock2();

        new Thread(()->{
            log.debug("start");
            basicLock2.x();
        },"t1").start();

        new Thread(()->{
            log.debug("start");
            basicLock2.y();
        },"t2").start();


    }
}
