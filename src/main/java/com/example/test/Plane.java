package com.example.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author Ellison_Pei
 * @date 2021/11/15 15:27
 * @since 1.0
 **/
public class Plane {
    public static void main(String[] args){
        BufferedReader buf = new BufferedReader(
                new InputStreamReader(System.in));
//        Scanner input = new Scanner(System.in);
        boolean inputIndex= true;
        int[]shuzu=new int[100];

        //随机生成数字存入数组
        for (int i=0;i<100;i++){
            shuzu[i] = new java.util.Random().nextInt(100);
        }

        do{
            try {
                System.out.println("Please in put the index of the shuzu[0-100]:");
                int index = Integer.parseInt(buf.readLine());
                System.out.println(shuzu[index]);
                inputIndex= false;//输出之后退出

            } catch (Exception ex) {
                System.out.println("out of bounds!");
                System.out.println("Please input again,index must be inputed from (0-100)");
            }
        }
        while (inputIndex);

    }
}
