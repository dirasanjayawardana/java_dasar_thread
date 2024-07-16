package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

public class ThreadTest {
  // Thread adalah proses ringan yang dijalankan pada proses aplikasi
  // class Thread adalah implementasi Concurency dan Parallel di java
  // secara default setiap aplikasi java berjalan, akan ada minimal satu thread yang berjalan yang bernama "main" (thread utama)


  @Test
  void mainThread() {
    // mendapatkan thread yang sedang berjalan saat ini
    var name = Thread.currentThread().getName();
    System.out.println(name);
  }


  @Test
  void createThread() {
    // Runnable (interface untuk membuat pekerjaan yang bisa dijalankan di Thread), akan berjalan secara asnchronous
    Runnable runnable = () -> {
      System.out.println("Hello from thread : " + Thread.currentThread().getName());
    };

    // menjalankan pekerjaan yg ada di Runnable di main thread
    runnable.run();

    // membuat Thread baru dan menjalankan Runnable, akan berjalan secara asnchronous
    var thread = new Thread(runnable);
    thread.start();

    System.out.println("Program Selesai");
  }


  @Test
  void threadSleep() throws InterruptedException {
    // Thread.sleep() (untuk mematikan sementara sebuah thread baru dijalankan, mirip seperti memberi delay)
    Runnable runnable = () -> {
      try {
        Thread.sleep(2_000L);
        System.out.println("Hello from thread : " + Thread.currentThread().getName());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    var thread = new Thread(runnable);
    thread.start();

    System.out.println("Program Selesai");

    Thread.sleep(3_000L);
  }


  @Test
  void threadJoin() throws InterruptedException {
    // thread.join() (menunggu sampai proses selesai, mirip seperti await)
    Runnable runnable = () -> {
      try {
        Thread.sleep(2_000L);
        System.out.println("Hello from thread : " + Thread.currentThread().getName());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    var thread = new Thread(runnable);
    thread.start();
    System.out.println("Menunggu Selesai");
    thread.join();
    System.out.println("Program Selesai");
  }


  @Test
  void threadInterrupt() throws InterruptedException {
    // Thread.interupt() (menghentikan/membatalkan proses yang sedang berjalan)
    Runnable runnable = () -> {
      for (int i = 0; i < 10; i++) {
        System.out.println("Runnable : " + i);
        try {
          Thread.sleep(1_000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    var thread = new Thread(runnable);
    thread.start();
    Thread.sleep(5_000);
    // memberi tahu bahwa ada interupt
    thread.interrupt();
    System.out.println("Menunggu Selesai");
    thread.join();
    System.out.println("Program Selesai");
  }


  @Test
  void threadInterruptCorrect() throws InterruptedException {
    // Thread.interupt() (menghentikan/membatalkan proses yang sedang berjalan)
    Runnable runnable = () -> {
      for (int i = 0; i < 10; i++) {
        // cek jika ada interupt, maka langsung return
        if(Thread.interrupted()){
          return;
        }

        System.out.println("Runnable : " + i);
        try {
          Thread.sleep(1_000L);
        } catch (InterruptedException e) {
          return;
        }
      }
    };

    var thread = new Thread(runnable);
    thread.start();
    Thread.sleep(5_000);
    // memberi tahu bahwa ada interupt, kemudian bisa di cek dengan Thread.interupted() di Runnable
    thread.interrupt();
    System.out.println("Menunggu Selesai");
    thread.join();
    System.out.println("Program Selesai");
  }


  @Test
  void threadName() {
    var thread = new Thread(() -> {
      System.out.println("Run in thread : " + Thread.currentThread().getName());
    });
    // merubah nama thread agar lebih informatif saat debuging, jika tidak dirubah maka defaultnya "Thread-keBerapa"
    thread.setName("DirApp");
    thread.start();
  }


  @Test
  void threadState() throws InterruptedException {
    // state (melihat status sebuah thread, apakah NEW, RUNNABLE, BLOCKED, WAITING, TERMINATED)
    var thread = new Thread(() -> {
      System.out.println(Thread.currentThread().getState());
      System.out.println("Run in thread : " + Thread.currentThread().getName());
    });

    thread.setName("DirApp");
    System.out.println(thread.getState());

    thread.start();
    thread.join();
    System.out.println(thread.getState());
  }
}
