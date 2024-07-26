package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ForkJoinTest {
  // Fork/Join merupakan fitur untuk mempercepat proses secara parallel dengan memanfaatkan semua cpu processor (sejak java 7)
  // secara sederhana Fork/Join akan melakukan FORK, memecah pekerjaan menjadi bagian kecil dan dieksekusi secara asnychronous
  // Setelah proses FORK selesai, proses JOIN akan dilakukan, yaitu menggabungkan hasil pekerjaan menjadi satu
  // Fork/Join Thread menggunakan work stealing algorythm, akan secara maksimal menjalankan pekerjaan disemua thread
  // jika ada thread yg sudah selesai, thread tersbut akan mencoba mencuri/mengambil pekerjaan dari queue dari thread lain


  @Test
  void create() {
    // membuat ForkJoinPool sesuai dengan jumlah total cpu (parallelism)
    var forkJoinPool1 = ForkJoinPool.commonPool();
    // membuat ForkJoinPool sesuai dengan jumlah parallelism
    var forkJoinPool2 = new ForkJoinPool(5);

    // membuat ForkJoinPool dengan executor sesuai dengan jumlah total cpu (parallelism), seperti ForkJoinPool.commonPool()
    var executor1 = Executors.newWorkStealingPool();
    // membuat ForkJoinPool dengan executor service sesuai dengan jumlah parallelism, sama seperti new ForkJoinPool(5);
    var executor2 = Executors.newWorkStealingPool(5);
  }


  // Recursive Action --> class untuk task yang tidak mengembalikan result, sepert Runnable
  @Test
  void recursiveAction() throws InterruptedException {
    var pool = ForkJoinPool.commonPool();
    List<Integer> integers = IntStream.range(0, 1000).boxed().collect(Collectors.toList());

    var task = new SimpleForkJoinTask(integers);
    pool.execute(task);

    pool.shutdown();
    pool.awaitTermination(1, TimeUnit.DAYS);
  }


  // Recursive Task --> class untuk task yang mengembalikan result, sepert Callable
  @Test
  void recursiveTask() throws ExecutionException, InterruptedException {
    var pool = ForkJoinPool.commonPool();
    List<Integer> integers = IntStream.range(0, 1000).boxed().collect(Collectors.toList());

    var task = new TotalRecursiveTask(integers);
    ForkJoinTask<Long> submit = pool.submit(task);

    Long aLong = submit.get();
    System.out.println(aLong);

    long sum = integers.stream().mapToLong(value -> value).sum();
    System.out.println(sum);

    pool.shutdown();
    pool.awaitTermination(1, TimeUnit.DAYS);
  }


  public static class SimpleForkJoinTask extends RecursiveAction {
    private List<Integer> integers;

    public SimpleForkJoinTask(List<Integer> integers) {
      this.integers = integers;
    }

    @Override
    protected void compute() {
      if (integers.size() <= 10) {
        // eksekusi
        doExecute();
      } else {
        // fork
        forkCompute();
      }
    }

    private void forkCompute() {
      List<Integer> integers1 = this.integers.subList(0, this.integers.size() / 2);
      List<Integer> integers2 = this.integers.subList(this.integers.size() / 2, this.integers.size());

      SimpleForkJoinTask task1 = new SimpleForkJoinTask(integers1);
      SimpleForkJoinTask task2 = new SimpleForkJoinTask(integers2);

      ForkJoinTask.invokeAll(task1, task2);
    }

    private void doExecute() {
      integers.forEach(integer -> System.out.println(Thread.currentThread().getName() + ":" + integer));
    }
  }


  public static class TotalRecursiveTask extends RecursiveTask<Long> {
    private List<Integer> integers;

    public TotalRecursiveTask(List<Integer> integers) {
      this.integers = integers;
    }

    @Override
    protected Long compute() {
      if (integers.size() <= 10) {
        return doCompute();
      } else {
        return forkCompute();
      }
    }

    private Long forkCompute() {
      List<Integer> integers1 = this.integers.subList(0, this.integers.size() / 2);
      List<Integer> integers2 = this.integers.subList(this.integers.size() / 2, this.integers.size());

      TotalRecursiveTask task1 = new TotalRecursiveTask(integers1);
      TotalRecursiveTask task2 = new TotalRecursiveTask(integers2);

      ForkJoinTask.invokeAll(task1, task2);

      return task1.join() + task2.join();
    }

    private Long doCompute() {
      return integers.stream().mapToLong(value -> value).peek(value -> {
        System.out.println(Thread.currentThread().getName() + " : " + value);
      }).sum();
    }
  }
}
