package com.dirapp.java_dasar_thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterLock {
  // Lock adalah alternatif dari low level synchronized dan manual await dan notify
  // Lock merupakan interface alternatif implementasi dari synchronized method dan synchronized statemnt
  // lock() --> untuk melakukan lock, unlock() --> untuk melepas lock
  // implementasi dari interface Lock adalah class ReentrantLock (untuk lock write dan read) dan ReentrantReadWriteLock (bisa memilih untuk lock write saja atau read saja)
  

  private Long value = 0L;

  final private Lock lock = new ReentrantLock();

  // pada increment() dilakukan lock, sehingga jika ada thread yang ingin eksekusi secara bersamaan, harus menunggu
  public void increment() {
    try {
      lock.lock();
      value++;
    } finally {
      lock.unlock();
    }
  }

  // pada getValue() tidak di lock, sehingga semua thread bisa akses secara bersamaan
  public Long getValue() {
    return value;
  }
}