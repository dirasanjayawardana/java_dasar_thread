package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

public class ThreadCommunicationTest {
  // Thread communication (melakukan komunikasi antara beberapa thread)
  // salah satu cara menggunakan sharing variabel

  private String message = null;


  @Test
  void manual() throws InterruptedException {
    // melakukan pengecekan manual (tidak disarankan, penggunaan cpu akan naik, tidak bisa diinterupt)
    var thread1 = new Thread(() -> {
      while (message == null){
        // menunggu sampai message diisi oleh thread yang lain, baru bisa keluar dari blok ini
      }
      System.out.println(message);
    });

    var thread2 = new Thread(() -> {
      message = "Dira Sanjaya Wardana";
    });

    thread2.start();
    thread1.start();

    thread2.join();
    thread1.join();
  }


  @Test
  void waitNotify() throws InterruptedException {
    // menggunakan wait() dan notify(), tersedia untuk semua object
    final var lock = new Object();

    var thread1 = new Thread(() -> {
      synchronized (lock){
        try {
          lock.wait(); // menunggu sampai ada triger dari notify() baru melanjutkan eksekusi program
          System.out.println(message);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    var thread2 = new Thread(() -> {
      synchronized (lock){
        message = "Dira Sanjaya Wardana";
        lock.notify(); // memberikan trigger ke sebuah thread lain untuk dilisten oleh wait()
      }
    });

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();
  }


  @Test
  void waitNotifyAll() throws InterruptedException {
    final var lock = new Object();

    var thread1 = new Thread(() -> {
      synchronized (lock){
        try {
          lock.wait(); // menunggu sampai ada triger dari notify() baru melanjutkan eksekusi program
          System.out.println(message);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    var thread3 = new Thread(() -> {
      synchronized (lock){
        try {
          lock.wait(); // menunggu sampai ada triger dari notify() baru melanjutkan eksekusi program
          System.out.println(message);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    var thread2 = new Thread(() -> {
      synchronized (lock){
        message = "Dira Sanjaya Wardana";
        lock.notifyAll(); // memberikan trigger ke semua thread untuk dilisten oleh wait()
      }
    });

    thread1.start();
    thread3.start();

    thread2.start();

    thread1.join();
    thread3.join();

    thread2.join();
  }
}
