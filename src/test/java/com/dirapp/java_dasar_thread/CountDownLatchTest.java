package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
  // CountDownLatch merupakan synchronizer yang digunakan untuk menunggu beberapa proses selesai
  // mirip seperti Semaphore, namun diawal counternya sudah ditentukan
  // setelah proses selesai, nilai countdown akan diturunkan
  // jika countdown sudah bernilai 0, maka yang melakukan wait bisa lanjut berjalan
  // cocok untuk beberapa proses yang berjalan secara async sampai semua proses selesai


  @Test
  void test() throws InterruptedException {

    final var countDownLatch = new CountDownLatch(5);
    final var executor = Executors.newFixedThreadPool(10);

    for (int i = 0; i < 5; i++) {
      executor.execute(() -> {
        try {
          System.out.println("Start Task");
          Thread.sleep(2000);
          System.out.println("Finish Task");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          // menurunkan countdown satu persatu
          countDownLatch.countDown();
        }
      });
    }

    executor.execute(() -> {
      try {
        // tunggu sampai countdown menjadi 0, baru jalankan task
        countDownLatch.await();
        System.out.println("Finish All Tasks");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }
}
