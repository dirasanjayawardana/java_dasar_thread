package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

public class DeadlockTest {
  // Deadlock adalah kondisi dimana beberapa thread saling menunggu satu sama lain
  // karena beberapa thread tersebut melakukan lock dan menunggu lock lain dari thread lain
  // sehingga semua thread tidak berjalan, karena hanya menunggu lock
  
  @Test
  void transfer() throws InterruptedException {
    var balance1 = new Balance(1_000_000L);
    var balance2 = new Balance(1_000_000L);

    var thread1 = new Thread(() -> {
      try {
        Balance.transfer(balance1, balance2, 500_000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    var thread2 = new Thread(() -> {
      try {
        Balance.transfer(balance2, balance1, 500_000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

    System.out.println("Balance 1 : " + balance1.getValue());
    System.out.println("Balance 2 : " + balance2.getValue());
  }
}
