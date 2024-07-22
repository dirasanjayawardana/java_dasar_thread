package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {
  // CyclicBarrier merupakan fitur yang bisa digunakan untuk saling menunggu
  // sampai jumlah thread yang menunggu terpenuhi
  // diawal ditentukan jumalh thread yang menunggu, jika sudah terpenuhi, maka secara otomatis proses menunggu akan selesai


  @Test
  void test() throws InterruptedException {

    final var cyclicBarrier = new CyclicBarrier(5);
    final var executor = Executors.newFixedThreadPool(10);

    for (int i = 0; i < 5; i++) {
      executor.execute(() -> {
        try {
          System.out.println("Waiting");
          // tunggu sampai jumlah thread yang menunggu terpenuhi (contohnya disini 5 thread)
          cyclicBarrier.await();
          System.out.println("Done Waiting");
        } catch (InterruptedException | BrokenBarrierException e) {
          e.printStackTrace();
        }
      });
    }

    executor.awaitTermination(1, TimeUnit.DAYS);
  }
}