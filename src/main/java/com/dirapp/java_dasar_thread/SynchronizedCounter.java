package com.dirapp.java_dasar_thread;

public class SynchronizedCounter {
  // Synchronization adalah fitur dimana untuk memaksa kode program hanya boleh diakses dan dieksekusi oleh satu thread saja
  // ini dapat mencegah adanya race condition
  // jika ada thread yang ingin mengakses data yang sama, harus menunggu 
  // synchronization dijava bisa menggunakan synchronized method (untuk sebuah method) atau menggunakan synchronized statemnt (untuk part code tertentu)

  // pada dasarnya ketika melakukan synchronized, secara otomatis java akan membuat intrinsic lock atau monitor lock
  // ketika synchronized method di panggil, maka thread akan mencoba mendapatkan intrinsic lock
  // setelah method selesai (sukses ataupun error), maka thread akan mengembalikan intrinsic lock


  private Long value = 0L;

  // menggunakan sychronized statement (harus menentukan blok mana yang akan di synchronized, dan di object mana/conto this)
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
