package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PharserTest {
  // Pharser merupakan fitur synchronizer mirip seperti CyclicBarier dan CoundownLatch namu lebih flexible
  // pada Pharser jumlah counter bisa berubah, menaikkan counter menggunakan method register() atau bulkRegister(int)
  // untuk menurunkan bisa menggunakan method arrieve() 
  // untuk menunggu sampai jumlah yang register teretntu await(int)


  @Test
  void countDownLatch() throws InterruptedException {
    final var phaser = new Phaser();
    final var executor = Executors.newFixedThreadPool(15);

    // menaikkan nilai counter
    phaser.bulkRegister(5);
    phaser.bulkRegister(5);

    for (int i = 0; i < 10; i++) {
      executor.execute(() -> {
        try {
          System.out.println("Start Task");
          Thread.sleep(2000);
          System.out.println("End Task");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          // menurunkan nilai counter
          phaser.arrive();
        }
      });
    }

    executor.execute(() -> {
      // tunggu sampai nilai tertentu, baru jalankan task
      phaser.awaitAdvance(0);
      System.out.println("All tasks done");
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void cyclicBarrier() throws InterruptedException {
    final var phaser = new Phaser();
    final var executor = Executors.newFixedThreadPool(15);

    phaser.bulkRegister(5);

    for (int i = 0; i < 5; i++) {
      executor.execute(() -> {
        // menurunkan nilai counter, dan langsung menunggu, defaultnya menunggu sampai 0
        phaser.arriveAndAwaitAdvance();
        System.out.println("DONE");
      });
    }

    executor.awaitTermination(1, TimeUnit.DAYS);
  }
}
