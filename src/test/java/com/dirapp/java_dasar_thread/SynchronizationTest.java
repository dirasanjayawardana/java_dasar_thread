package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

public class SynchronizationTest {
  // Synchronization adalah fitur dimana untuk memaksa kode program hanya boleh diakses dan dieksekusi oleh satu thread saja
  // ini dapat mencegah adanya race condition
  // jika ada thread yang ingin mengakses data yang sama, harus menunggu 
  // synchronization dijava bisa menggunakan synchronized method (untuk sebuah method) atau menggunakan synchronized statemnt (untuk part code tertentu)

  // pada dasarnya ketika melakukan synchronized, secara otomatis java akan membuat intrinsic lock atau monitor lock
  // ketika synchronized method di panggil, maka thread akan mencoba mendapatkan intrinsic lock
  // setelah method selesai (sukses ataupun error), maka thread akan mengembalikan intrinsic lock

  @Test
  void counter() throws InterruptedException {
    var counter = new SynchronizedCounter();
    Runnable runnable = () -> {
      for (int i = 0; i < 1_000; i++) {
        counter.increment();
      }
    };

    var thread1 = new Thread(runnable);
    var thread2 = new Thread(runnable);
    var thread3 = new Thread(runnable);

    thread1.start();
    thread2.start();
    thread3.start();

    thread1.join();
    thread2.join();
    thread3.join();

    System.out.println(counter.getValue());
  }
}
