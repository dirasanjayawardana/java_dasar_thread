package com.dirapp.java_dasar_thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CounterReadWriteLock {
  // Lock adalah alternatif dari low level synchronized dan manual await dan notify
  // Lock merupakan interface alternatif implementasi dari synchronized method dan synchronized statemnt
  // lock() --> untuk melakukan lock, unlock() --> untuk melepas lock
  // implementasi dari interface Lock adalah class ReentrantLock (untuk lock write dan read) dan ReentrantReadWriteLock (bisa memilih untuk lock write saja atau read saja)

  private Long value = 0L;

  final private ReadWriteLock lock = new ReentrantReadWriteLock();

  // pada increment() dilakukan lock, sehingga jika ada thread yang ingin eksekusi secara bersamaan, harus menunggu
  public void increment() {
    try {
      lock.writeLock().lock();
      value++;
    } finally {
      lock.writeLock().unlock();
    }
  }

  // pada getValue() dilakukan lock, sehingga jika ada thread yang ingin eksekusi secara bersamaan, harus menunggu
  public Long getValue() {
    try {
      lock.readLock().lock();
      return value;
    } finally {
      lock.readLock().unlock();
    }
  }
}