package com.dirapp.java_dasar_thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ExchangerTest {
  // Exchanger merupakan fitur synchronizer untuk melakukan pertukaran data antar thread
  // jika data belum tersedia, maka thread yang melakukan pertukaran akan menunggu sampai ada thread lain yang melakukan pertukaran

  // exchanger.exchange(DataYgDikirim) --> returnnya adalah data yg diterima

  @Test
  void test() throws InterruptedException {

    final var exchanger = new Exchanger<String>();
    final var executor = Executors.newFixedThreadPool(10);

    executor.execute(() -> {
      try {
        System.out.println("Thread 1 : Send : First");
        Thread.sleep(1000);
        var result = exchanger.exchange("First");
        System.out.println("Thread 1 : Receive : " + result);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    executor.execute(() -> {
      try {
        System.out.println("Thread 2 : Send : Second");
        Thread.sleep(2000);
        var result = exchanger.exchange("Second");
        System.out.println("Thread 2 : Receive : " + result);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }
}
