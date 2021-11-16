package com.example.test;

import java.util.Scanner;

/**
 *
 * @author Ellison_Pei
 * @date 2021/11/15 15:27
 * @since 1.0
 **/
public class Plane {

    private static int[] nums = new int[100];

    public static void main(String[] args) {
//        initArray();
//        Scanner sc = new Scanner(System.in);
//        String index = sc.next();
//        System.out.println(getValue(Integer.parseInt(index)));
        System.out.println(11);
    }

    /**
     * 生成100以内的随机数 100次
     */
    public static void initArray() {
        for (int i = 0; i < 100; i++) {
            int r = (int) (Math.random() * 100000) % 100;
            nums[i] = r;
        }
    }

    private static int getValue(int i){
        int val;
        try {
            val = nums[i];
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            throw new IndexOutOfBoundsException("下标越界");
        }

        return val;
    }
}
