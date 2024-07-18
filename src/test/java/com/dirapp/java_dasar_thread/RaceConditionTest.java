package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

public class RaceConditionTest {
  // Race Condition adalah kondisi dimana sebuah data diubah secara bersamaan oleh beberapa thread
  // menyebabkan hasil akhir yang tidak sesuai, salah satu problem yang sering terjadi pada aplikasi concurrent atau parallel

  
  @Test
  void counter() throws InterruptedException {
    var counter = new Counter();
    Runnable runnable = () -> {
      for (int i = 0; i < 1_000; i++) {
        counter.increment();
      }
    };
    
    var thread1 = new Thread(runnable);
    var thread2 = new Thread(runnable);
    var thread3 = new Thread(runnable);
    
    // harusnya hasil dari counter adalah 3000 karena runnable dijalankan 3 kali
    // namun pada kenyataannya hasil dari counter tidak 3000, karena terjadi race Condition
    // dimana data pada class Counter diubah pada waktu yang bersamaan
    
    // thread1.start();
    // thread2.start();
    // thread3.start();

    // thread1.join();
    // thread2.join();
    // thread3.join();

    // System.out.println(counter.getValue());


    // dengan menggunakan join() pada setiap thread, memastikan bahwa proses di salah datu thread selesai
    // baru dilanjutkan untuk proses selanjutnya, ini mencegah adanya race Condition
    // cara lain bisa menggunakan Synchronization
    thread1.start();
    thread1.join();

    thread2.start();
    thread2.join();

    thread3.start();
    thread3.join();

    System.out.println(counter.getValue());
  }
}
