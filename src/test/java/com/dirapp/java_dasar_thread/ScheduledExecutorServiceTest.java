package com.dirapp.java_dasar_thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ScheduledExecutorServiceTest {
  // ScheduledExecutorService merupakan turunan dari ExecutorService
  // digunakan untuk melakukan asynchronous task yang terjadwal, mirip seperti Timer, kini menggantikan penggunaan Timer
  // untuk membuat ScheduledExecutore bisa menggunakan Executor.newSingleThreadScheduledExecutor() atau
  // Executor.newScheduledThreadPool(int poolSize)

  // executor.schedule(Runnable/Callable, long delay, TimeUnit unit) --> untuk schedule satu kali sesuai delay
  // executor.scheduleAtFixedRate(Runnable/Callable, long initialDelay, long period, TimeUnit unit) --> untuk schedule terus menerus secara periodik
  // getDelay(TimeUnit unit) --> untuk mengecek berapa lama lagi task akan dieksekusi


  @Test
  void delayedJob() throws InterruptedException {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

    ScheduledFuture<?> future = executor.schedule(() -> System.out.println("Hello Scheduled"), 5, TimeUnit.SECONDS);

    System.out.println(future.getDelay(TimeUnit.MILLISECONDS));

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void periodicJob() throws InterruptedException {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

    ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> System.out.println("Hello Scheduled"), 2, 2, TimeUnit.SECONDS);

    System.out.println(future.getDelay(TimeUnit.MILLISECONDS));

    executor.awaitTermination(1, TimeUnit.DAYS);
  }
}
