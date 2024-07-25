package com.dirapp.java_dasar_thread;

public class UserService {
  // ThreadLocal merupakan fitur dijava untuk menyimpan data
  // ThreadLocal memungkinkan untuk menyimpan data yang hanya bisa digunakan di suatu thread
  // Thread yang melakukan get harus sama dengan thread yang melakukan set
  // set(T value) --> memasukkan data
  // T get() --> mengambil data
  // remove() --> menghapus data

  final ThreadLocal<String> threadLocal = new ThreadLocal<>();
  // private String user;

  public void setUser(String user) {
    threadLocal.set(user);
    // this.user = user;
  }

  public void doAction() {
    var user = threadLocal.get();
    System.out.println(user + " do action");
  }

}