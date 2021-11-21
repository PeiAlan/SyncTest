package com.example.jmm;

import lombok.extern.slf4j.Slf4j;

/**
 * JVM 指令详解
 * http://gityuan.com/2015/10/24/jvm-bytecode-grammar/
 *
 * 有序性测试
 *
 *  java    -XX:+UnlockDiagnosticVMOptions
 *          -XX:+PrintAssembly
 *          -Xcomp
 *          -XX:CompileCommand=dontinline,*VolatileTest3.readAndWrite
 *          -XX:CompileCommand=compileonly,*VolatileTest3
 * path : LD_LIBRARY_PATH=/Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre/lib/server
 *
 * @author Ellison_Pei
 * @date 2021/11/19 11:03
 * @since 1.0
 **/
public class VolatileTest3 {
    int a;
    volatile int v1 = 1;
    int v2 = 2;
    void readAndWrite() {
        // 第一个volatile读
        int i = v1;
        // 第二个volatile读
        int j = v2;
        // 普通写
        a = i + j;
        // 第一个volatile写
        v1 = i + 1;
        // 第二个 volatile写
        v2 = j * 2;
    }

    public static void main(String[] args) {
        VolatileTest3 volatileTest3 = new VolatileTest3();
        volatileTest3.readAndWrite();
    }
    // … // 其他方法
}