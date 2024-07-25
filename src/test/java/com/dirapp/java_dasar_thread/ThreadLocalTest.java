package com.dirapp.java_dasar_thread;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
  // ThreadLocal merupakan fitur dijava untuk membuat variabel dengan scope terbatas pada thread
  // ThreadLocal memungkinkan untuk menyimpan data yang hanya bisa digunakan di suatu thread
  // Thread yang melakukan get harus sama dengan thread yang melakukan set
  // set(T value) --> memasukkan data
  // T get() --> mengambil data
  // remove() --> menghapus data


  @Test
  void test() throws InterruptedException {

    final var random = new Random();
    final var userService = new UserService();
    final var executor = Executors.newFixedThreadPool(10);

    for (int i = 0; i < 50; i++) {
      final var index = i;
      executor.execute(() -> {
        try {
          userService.setUser("User-" + index);
          Thread.sleep(1000 + random.nextInt(3000));
          userService.doAction();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    executor.awaitTermination(1, TimeUnit.DAYS);
  }
}
