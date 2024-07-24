package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.*;

public class BlockingQueueTest {
  // Collection yang terdapat di package java.util.concurrent dikhususkan untuk concurrent programming, sehingga thread safe
  // secara garis besar dibagi menadi dua:
  // Blocking queue --> turunan dari Queue, dikhususkan untuk tipe collection FIFO (First In First Out), mirip ThreadPool Queue
  // ConcurrentMap --> turunan dari Map, dikhususkan untuk Map yang thread safe

  // add(e), offer(e), put(e), offer(e, time, unit) --> menambah data kedalam queue, jika penuh akan throw exception
  // remove(), poll(), take(), poll(time, unit) --> menghapus dan mengambil data di queue
  // element(), peek()


  @Test
  void arrayBlockingQueue() throws InterruptedException {
    // implementasi BlockingQueue dengan ukuran fix
    final var queue = new ArrayBlockingQueue<String>(5);
    final var executor = Executors.newFixedThreadPool(20);

    // memasukkan data
    for (int i = 0; i < 10; i++) {
      executor.execute(() -> {
        try {
          queue.put("Data");
          System.out.println("Finish Put Data");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    // mengambil data
    executor.execute(() -> {
      while (true) {
        try {
          Thread.sleep(2000);
          var value = queue.take();
          System.out.println("Receive data : " + value);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void linkedBlockingQueue() throws InterruptedException {
    // implementasi BlockingQueue dengan ukuran yang bisa bertambah
    final var queue = new LinkedBlockingQueue<String>();
    final var executor = Executors.newFixedThreadPool(20);

    for (int i = 0; i < 10; i++) {
      executor.execute(() -> {
        try {
          queue.put("Data");
          System.out.println("Finish Put Data");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    executor.execute(() -> {
      while (true) {
        try {
          Thread.sleep(2000);
          var value = queue.take();
          System.out.println("Receive data : " + value);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void priorityBlockingQueue() throws InterruptedException {
    // implementasi BlockingQueue dengan otomatis berurut seusuai dengan compareable nya
    final var queue = new PriorityBlockingQueue<Integer>(10, Comparator.reverseOrder());
    final var executor = Executors.newFixedThreadPool(20);

    for (int i = 0; i < 10; i++) {
      final var index = i;
      executor.execute(() -> {
        queue.put(index);
        System.out.println("Finish Put Data");
      });
    }

    executor.execute(() -> {
      while (true) {
        try {
          Thread.sleep(2000);
          var value = queue.take();
          System.out.println("Receive data : " + value);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void delayedQueue() throws InterruptedException {
    // implementasi BlockingQueue dimana tidak ada data yang bisa diambil sebelum waktu delay selesai
    final var queue = new DelayQueue<ScheduledFuture<String>>();
    final var executor = Executors.newFixedThreadPool(20);
    final var executorScheduled = Executors.newScheduledThreadPool(10);

    for (int i = 1; i <= 10; i++) {
      final var index = i;
      queue.put(executorScheduled.schedule(() -> "Data " + index, i, TimeUnit.SECONDS));
    }

    executor.execute(() -> {
      while (true) {
        try {
          var value = queue.take();
          System.out.println("Receive data : " + value.get());
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }
      }
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void synchronousQueue() throws InterruptedException {
    // implementasi BlockingQueue dimana thread yang menambah data harus menunggu sampai ada thread yg mengambil data, begitu juga sebaliknya
    final var queue = new SynchronousQueue<String>();
    final var executor = Executors.newFixedThreadPool(20);

    for (int i = 0; i < 10; i++) {
      final var index = i;
      executor.execute(() -> {
        try {
          queue.put("Data-" + index);
          System.out.println("Finish Put Data : " + index);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    executor.execute(() -> {
      while (true) {
        try {
          Thread.sleep(2000);
          var value = queue.take();
          System.out.println("Receive data : " + value);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void blockingDeque() throws InterruptedException {
    // Blocking Deque merupakan turunan dari BlockingQueue, mendukung FIFO (First In First Out) dan LIFO (Last In First Out)
    final var queue = new LinkedBlockingDeque<String>();
    final var executor = Executors.newFixedThreadPool(20);

    for (int i = 0; i < 10; i++) {
      final var index = i;
      try {
        queue.putLast("Data-" + index);
        System.out.println("Finish Put Data : " + index);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    executor.execute(() -> {
      while (true) {
        try {
          Thread.sleep(2000);
          var value = queue.takeFirst();
          System.out.println("Receive data : " + value);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void transferQueue() throws InterruptedException {
    // TransferQueue merupakan turunan dari BlockingQueue yang membolehkan pengirim data ke queue menunggu sampai data ada yang menerima
    final var queue = new LinkedTransferQueue<String>();
    final var executor = Executors.newFixedThreadPool(20);

    for (int i = 0; i < 10; i++) {
      final var index = i;
      executor.execute(() -> {
        try {
          queue.transfer("Data-" + index);
          System.out.println("Finish Put Data : " + index);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    executor.execute(() -> {
      while (true) {
        try {
          Thread.sleep(2000);
          var value = queue.take();
          System.out.println("Receive data : " + value);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }
}
