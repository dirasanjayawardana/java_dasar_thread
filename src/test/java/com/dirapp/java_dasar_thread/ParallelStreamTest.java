package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStreamTest {
  // Stream bisa dijalankan secara parallel, menggunakan method parallel()
  // secara default, Parallel Stream akan dijalankan di ForkJoinPool
  // dimana akan dirunning secara default menggunakan Thread sejumlah CPU total

  
  @Test
  void parallel() {
    Stream<Integer> stream = IntStream.range(0, 1000).boxed();

    stream.parallel().forEach(integer -> {
      System.out.println(Thread.currentThread().getName() + " : " + integer);
    });
  }


  @Test
  void customPool() {
    // menjalankan parallel stream dengan menentukan ingin menggunakan berapa thread
    var pool = new ForkJoinPool(1);
    ForkJoinTask<?> task = pool.submit(() -> {
      Stream<Integer> stream = IntStream.range(0, 1000).boxed();
      stream.parallel().forEach(integer -> {
        System.out.println(Thread.currentThread().getName() + " : " + integer);
      });
    });

    task.join();
  }
}
