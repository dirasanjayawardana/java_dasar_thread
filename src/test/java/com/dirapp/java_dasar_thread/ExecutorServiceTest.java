package com.dirapp.java_dasar_thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ExecutorServiceTest {
  // ThreadPoolExecutor merupakan implementasi dari interface Executor dan ExecutorService
  // ExecutorService lebih umum digunakan untuk eksekusi Runnable dibanding ThreadPoolService
  // Executors.newSingleThreadExecutor(); membuat threadpool dengan jumlah pool 1
  // Executors.newFixedThreadPool(n); membuat threadpool dengan jumlah pool tertentu
  // Executors.newCacheThreadPool(); membuat threadpool dengan jumlah thread bisa bertambah tak terbatas
  

  @Test
  void testExecutorService() throws InterruptedException {
    // membuat threadpool dengan single thread
    ExecutorService executor = Executors.newSingleThreadExecutor();

    for (int i = 0; i < 100; i++) {
      executor.execute(() -> {
        try {
          Thread.sleep(1000);
          System.out.println("Runnable in thread : " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void testExecutorServiceFix() throws InterruptedException {
    // membuat threadpool dengan jumlah thread tertentu
    ExecutorService executor = Executors.newFixedThreadPool(10);

    for (int i = 0; i < 100; i++) {
      executor.execute(() -> {
        try {
          Thread.sleep(1000);
          System.out.println("Runnable in thread : " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.DAYS);
  }
}
