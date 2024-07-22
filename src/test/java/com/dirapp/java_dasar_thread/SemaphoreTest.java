package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
  // Semaphore merupakan class yang digunakan untuk manage data counter
  // nilai counter bisa naik, namun ada batas maksimalnya, jika batas maksimalnya sudah tercapai
  // semua thread yang akan mencoba menaikkan harus menunggu, sampai ada thread lain yang menurunkan nilai counter
  // cocok untuk membatasi jumlah task yang running

  // new Semaphore(int jumlahMax) --> membuat object Semaphore
  // .acquire() --> untuk menaikkan 1 counter
  // .acquire(int jumlah) --> menaikkan counter sesuai jumlah
  // .release() --> menurunkan 1 counter
  // .release(int jumlah) --> menurunkan counter sesuai jumlah


  @Test
  void test() throws InterruptedException {
    final var semaphore = new Semaphore(10);
    final var executor = Executors.newFixedThreadPool(100);

    for (int i = 0; i < 1000; i++) {
      executor.execute(() -> {
        try {
          // akan dibatasi 10 task yang jalan, jika ada lebih dari 10 task, maka harus menunggu sampai ada .release()
          semaphore.acquire();
          Thread.sleep(1000);
          System.out.println("Finish");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          semaphore.release();
        }
      });
    }

    executor.awaitTermination(1, TimeUnit.DAYS);
  }
}
