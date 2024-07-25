package com.dirapp.java_dasar_thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ThreadLocalRandomTest {
  // ketika menggunakan object Random secara paralel, ini akan melakukan sharing variabel
  // ini membuat class Random tidak aman dan juga lambat
  // class ThreadLocalRandom merupakan class mirip seperti ThreadLocal, namun khusus untuk object Random
  // sehingga bisa membuat angka random tanpa khawatir race condition, karenya objectnya berbeda setiap threadnya

  
  @Test
  void test() throws InterruptedException {
    final var executor = Executors.newFixedThreadPool(100);

    for (int i = 0; i < 100; i++) {
      executor.execute(() -> {
        try {
          Thread.sleep(1000);
          var number = ThreadLocalRandom.current().nextInt();
          System.out.println(number);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void stream() throws InterruptedException {
    final var executor = Executors.newFixedThreadPool(10);

    executor.execute(() -> {
      ThreadLocalRandom.current().ints(1000, 0, 1000)
          .forEach(System.out::println);
    });

    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.DAYS);
  }
}
