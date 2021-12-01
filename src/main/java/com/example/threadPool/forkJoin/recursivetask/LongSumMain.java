package com.example.threadPool.forkJoin.recursivetask;


import com.example.threadPool.forkJoin.util.Utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


/**
 * @author Fox
 *
 * 利用ForkJoinPool计算1亿个整数的和
 */
public class LongSumMain {
	// 获取逻辑处理器数量 12
	static final int NCPU = Runtime.getRuntime().availableProcessors();

	static long calcSum;


	public static void main(String[] args) throws Exception {
		//准备数组
		int[] array = Utils.buildRandomIntArray(100000000);

		Instant now = Instant.now();
		// 单线程计算数组总和
		calcSum = seqSum(array);
		System.out.println("seq sum=" + calcSum);
		System.out.println("执行时间："+ Duration.between(now,Instant.now()).toMillis());

		//递归任务
		LongSum ls = new LongSum(array, 0, array.length);
		// 构建ForkJoinPool
  		ForkJoinPool fjp  = new ForkJoinPool(NCPU);

		now = Instant.now();
		//ForkJoin计算数组总和
		ForkJoinTask<Long> result = fjp.submit(ls);
		System.out.println("forkjoin sum=" + result.get());
		System.out.println("执行时间："+ Duration.between(now,Instant.now()).toMillis());

		fjp.shutdown();

		now = Instant.now();
		//并行流计算数组总和
		Long sum = (Long) IntStream.of(array).asLongStream().parallel().sum();
		System.out.println("IntStream sum="+sum);
		System.out.println("执行时间："+ Duration.between(now,Instant.now()).toMillis());

	}


	static long seqSum(int[] array) {
		long sum = 0;
		for (int i = 0; i < array.length; ++i) {
			sum += array[i];
		}
		return sum;
	}
}