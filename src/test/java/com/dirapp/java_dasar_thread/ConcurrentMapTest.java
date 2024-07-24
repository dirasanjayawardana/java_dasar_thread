package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentMapTest {
  // Collection yang terdapat di package java.util.concurrent dikhususkan untuk concurrent programming, sehingga thread safe
  // secara garis besar dibagi menadi dua:
  // Blocking queue --> turunan dari Queue, dikhususkan untuk tipe collection FIFO (First In First Out), mirip ThreadPool Queue
  // ConcurrentMap --> turunan dari Map, dikhususkan untuk Map yang thread safe


  @Test
  void concurrentMap() throws InterruptedException {
    final var countDown = new CountDownLatch(100);
    final var map = new ConcurrentHashMap<Integer, String>();
    final var executor = Executors.newFixedThreadPool(100);

    for (int i = 0; i < 100; i++) {
      final var index = i;
      executor.execute(() -> {
        try {
          Thread.sleep(1000);
          map.putIfAbsent(index, "Data-" + index);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          countDown.countDown();
        }
      });
    }

    executor.execute(() -> {
      try {
        countDown.await();
        map.forEach((integer, s) -> System.out.println(integer + " : " + s));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    executor.awaitTermination(1, TimeUnit.DAYS);
  }


  @Test
  void testCollection() {
    List<String> list = List.of("Dira", "Sanjaya", "Wardana");
    List<String> synchronizedList = Collections.synchronizedList(list);
  }
}
