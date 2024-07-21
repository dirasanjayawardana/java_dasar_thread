package com.dirapp.java_dasar_thread;

import java.util.concurrent.atomic.AtomicLong;

public class CounterAtomic {
  // package atomic berisikan class-class yang mendukung lock-free dan thread-safe programming pada single variabel
  // setiap object Atomic class akan mengelola data yang diakses dan di update menggunakan method yg telah disediakan
  // sehinnga atomic tidak perlu melakukan sychronized secara manual

  // contoh lain jika valuenya berupa object menggunakan AtomicReference<Object>
  private AtomicLong value = new AtomicLong(0L);

  public void increment() {
    value.incrementAndGet();
  }

  public Long getValue() {
    return value.get();
  }

}
