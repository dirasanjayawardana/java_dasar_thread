package com.dirapp.java_dasar_thread;

public class DeamonApp {
  // secara default, setiap membuat Thread, maka itu disebut user Thread
  // secara default java akan menunggu semua user Thread selesai sebelum program berhenti
  // Deamon Thread tidak akan ditunggu jika memang program java akan berhenti, kecuali dengan System.exit()
  // cara merubah user Thread menjadi Deamon Thread dengan setDeamon(true)

  public static void main(String[] args) {

    var thread = new Thread(() -> {
      try {
        Thread.sleep(3000);
        System.out.println("Run Thread");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    thread.setDaemon(true);
    thread.start();
  }
}
