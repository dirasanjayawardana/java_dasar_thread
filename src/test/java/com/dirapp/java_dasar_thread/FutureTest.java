package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureTest {
  // Callable<T> mirip seperti Runnable, namun pada Callable mengembalikan data
  // jika untuk eksekusi Runnable menggunakan .execute(), untuk eksekusi Callable menggunakan .submit()
  // untuk eksekusi banyak callable sekaligus dengan method .invokeAll()
  // untuk eksekusi beberaka Callable, dan hanya mengambil hasil yang paling cepat saja, dengan method .invokeAny()
  // Future merupakan representasi data yang akan dikembalikan oleh proses asnchronous, mirip seperti promise
  // T get() --> mengambil seluruh result data, jika belum ada, maka akan ditunggu sampai ada
  // T get(timeout, TimeUnit) --> mengambil result data, jika belum ada, akan ditunggu sampai timeout
  // void cancle(mayInterupt) --> membatalkan proses Callable, dan apakah diperbolehkan diinterupt
  // boolean isCanclled() --> mengecek apakah future dibatalkan
  // boolean idDone() --> mengecek apakah future telah selesai


  @Test
  void testFuture() throws ExecutionException, InterruptedException {
    var executor = Executors.newSingleThreadExecutor();

    Callable<String> callable = () -> {
      Thread.sleep(5000);
      return "hi";
    };

    Future<String> future = executor.submit(callable);
    System.out.println("Selesai Future");

    while (!future.isDone()) {
      System.out.println("Waiting future");
      Thread.sleep(1000);
    }

    String value = future.get();
    System.out.println(value);
  }


  @Test
  void testFutureCancel() throws ExecutionException, InterruptedException {
    var executor = Executors.newSingleThreadExecutor();

    Callable<String> callable = () -> {
      Thread.sleep(5000);
      return "hi";
    };

    Future<String> future = executor.submit(callable);
    System.out.println("Selesai Future");

    Thread.sleep(2000);
    future.cancel(true);

    System.out.println(future.isCancelled());
    String value = future.get();
    System.out.println(value);
  }


  // invokeAll() --> eksekusi banyak Callable sekaligus, akan menunggu semua selesai dan baru diambil datanya
  @Test
  void invokeAll() throws InterruptedException, ExecutionException {
    var executor = Executors.newFixedThreadPool(10);

    List<Callable<String>> callables = IntStream.range(1, 11).mapToObj(value -> (Callable<String>) () -> {
      Thread.sleep(value * 500L);
      return String.valueOf(value);
    }).collect(Collectors.toList());

    var futures = executor.invokeAll(callables);

    for (Future<String> stringFuture : futures) {
      System.out.println(stringFuture.get());
    }
  }


  // invokeAny() --> eksekusi beberaka Callable, dan hanya mengambil data hasil yang paling cepat saja
  @Test
  void invokeAny() throws InterruptedException, ExecutionException {
    var executor = Executors.newFixedThreadPool(10);

    List<Callable<String>> callables = IntStream.range(1, 11).mapToObj(value -> (Callable<String>) () -> {
      Thread.sleep(value * 500L);
      return String.valueOf(value);
    }).collect(Collectors.toList());

    var value = executor.invokeAny(callables);
    System.out.println(value);
  }
}
