package com.dirapp.java_dasar_thread;

import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.Test;

public class LockTest {
  // Lock adalah alternatif dari low level synchronized dan manual await dan notify
  // Lock merupakan interface alternatif implementasi dari synchronized method dan synchronized statemnt
  // lock() --> untuk melakukan lock, unlock() --> untuk melepas lock
  // implementasi dari interface Lock adalah class ReentrantLock

  // Condition merupakan interface untuk alternatif lain dari monitor method (wait, notify, notifyAll)
  // await() --> menunggu sampai ada triger dari signal() baru melanjutkan eksekusi program
  // signal() --> memberikan trigger ke sebuah thread lain untuk dilisten oleh await()
  // signalAll() --> memberikan trigger ke semua thread untuk dilisten oleh await()
  // ketika melakukan await, maka lock akan otomatis diunlock


  @Test
  void testLock() throws InterruptedException {
    var counter = new CounterLock();
    Runnable runnable = () -> {
      for (int i = 0; i < 1_000_000; i++) {
        counter.increment();
      }
    };

    var thread1 = new Thread(runnable);
    var thread2 = new Thread(runnable);
    var thread3 = new Thread(runnable);

    thread1.start();
    thread2.start();
    thread3.start();

    thread1.join();
    thread2.join();
    thread3.join();

    System.out.println(counter.getValue());
  }


  @Test
  void testReadWriteLock() throws InterruptedException {
    var counter = new CounterReadWriteLock();
    Runnable runnable = () -> {
      for (int i = 0; i < 1_000_000; i++) {
        counter.increment();
      }
    };

    var thread1 = new Thread(runnable);
    var thread2 = new Thread(runnable);
    var thread3 = new Thread(runnable);

    thread1.start();
    thread2.start();
    thread3.start();

    thread1.join();
    thread2.join();
    thread3.join();

    System.out.println(counter.getValue());
  }


  String message;


  @Test
  void condition() throws InterruptedException {
    // Condition merupakan interface untuk alternatif lain dari monitor method (wait, notify, notifyAll)
    // await() --> menunggu sampai ada triger dari signal() baru melanjutkan eksekusi program
    // signal() --> memberikan trigger ke sebuah thread lain untuk dilisten oleh await()
    // signalAll() --> memberikan trigger ke semua thread untuk dilisten oleh await()
    // ketika melakukan await, maka lock akan otomatis diunlock

    var lock = new ReentrantLock();
    var condition = lock.newCondition();

    var thread1 = new Thread(() -> {
      try {
        lock.lock();
        condition.await();
        System.out.println(message);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    });

    var thread3 = new Thread(() -> {
      try {
        lock.lock();
        condition.await();
        System.out.println(message);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    });

    var thread2 = new Thread(() -> {
      try {
        lock.lock();
        Thread.sleep(2000);
        message = "Dira Sanjaya Wardana";
        condition.signalAll();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
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
