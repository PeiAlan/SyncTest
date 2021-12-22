package com.example.testJol.sync;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/12/22 15:03
 * @since 1.0
 **/
public class StringBufferEntity {

    StringBuffer buffer = new StringBuffer();

    /**
     * 锁粗化
     */
    public void append() {
        buffer.append("aaa").append(" bbb").append(" ccc");
    }

    /**
     * 锁消除
     * -XX:+EliminateLocks 开启锁消除(jdk8默认开启）
     * -XX:-EliminateLocks 关闭锁消除
     *
     * @param str1
     * @param str2
     */
    public void append(String str1, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str1).append(str2);
    }
}
