package com.dirapp.java_dasar_thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ThreadPoolTest {
  // Thread merupakan object yang sangat mahal, pembuatan object thread bisa memakan memory 512kb-1mb
  // jika pembuatan thread tidak dimanajement dengan baik, bisa OutOfMemory
  // Threadpool digunakan untuk management thread secara otomatis
  // Threadpool juga bisa melakukan reusable thread yang sudah selesai berkerja
  // menggunakan ThreadPoolExecutor


  @Test
  void create() {
    int minThread = 10; // core pool (minimal thread yang akan dibuat)
    int maxThread = 100; // maximal thread yang akan dibuat
    long alive = 1; // minimal thread yang aktif
    TimeUnit aliveTime = TimeUnit.MINUTES; // berapa lamaa thread akan dihapus jika tidak berkerja
    ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100); // antrian untuk menampung pekerjaan yang dikirim ke threadpool

    Executor threadpool = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue);
  }


  @Test
  void executeRunnable() throws InterruptedException {
    var minThread = 10;
    var maxThread = 100;
    var alive = 1;
    var aliveTime = TimeUnit.MINUTES;
    var queue = new ArrayBlockingQueue<Runnable>(100);

    Executor executor = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue);

    Runnable runnable = () -> {
      try {
        Thread.sleep(5000);
        System.out.println("Runnable from thread : " + Thread.currentThread().getName());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };
    executor.execute(runnable);

    Thread.sleep(6000);
  }


  @Test
  void shutdown() throws InterruptedException {
    // ketika sudah selesai menggunakan threadpool dan tidak akan menggunakannya lagi, baiknya ThreadPoolnya dimatikan
    // executor.shutDown(); jika ada pekerjaan yang belum dikerjakan akan diabaikan
    // executor.shutDownNow(); jika ada pekerjaan yang belum dikerjakan akan dikembalikan
    // executor.awaitTermination(); jika ada perkerjaan yang belum selesai akan ditunggu sampai selesai baru dishutdown
    var minThread = 10;
    var maxThread = 100;
    var alive = 1;
    var aliveTime = TimeUnit.MINUTES;
    var queue = new ArrayBlockingQueue<Runnable>(1000);

    var executor = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue);

    for (int i = 0; i < 1000; i++) {
      final var task = i;
      Runnable runnable = () -> {
        try {
          Thread.sleep(1000);
          System.out.println("Task " + task + " from thread : " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      };
      executor.execute(runnable);
    }
    // executor.shutdownNow();
    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void rejected() throws InterruptedException {
    var minThread = 10;
    var maxThread = 100;
    var alive = 1;
    var aliveTime = TimeUnit.MINUTES;
    var queue = new ArrayBlockingQueue<Runnable>(10);
    var rejectedHandler = new LogRejectedExecutionHandler(); // untuk menghandle ketika terjadi kelebihan pekerjaan

    var executor = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue, rejectedHandler);

    for (int i = 0; i < 1000; i++) {
      final var task = i;
      Runnable runnable = () -> {
        try {
          Thread.sleep(1000);
          System.out.println("Task " + task + " from thread : " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      };
      executor.execute(runnable);
    }
    // executor.shutdownNow();
    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  public static class LogRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
      System.out.println("Task " + r + " is rejected");
    }
  }
}
