package com.dirapp.java_dasar_thread;

public class SynchronizedCounter {
  // Synchronization adalah fitur dimana untuk memaksa kode program hanya boleh diakses dan dieksekusi oleh satu thread saja
  // ini dapat mencegah adanya race condition
  // jika ada thread yang ingin mengakses data yang sama, harus menunggu 
  // synchronization dijava bisa menggunakan synchronized method atau menggunakan synchronized statemnt

  private Long value = 0L;

  // menggunakan sychronized statement
  public void increment() {
    synchronized (this) {
      value++;
    }
  }

  // atau menggunakan sychronized method
  // public synchronized void increment() {
  //   value++;
  // }

  public Long getValue() {
    return value;
  }
}
