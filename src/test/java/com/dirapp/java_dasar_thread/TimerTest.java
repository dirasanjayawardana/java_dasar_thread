package com.dirapp.java_dasar_thread;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
  // Timer merupakan class untuk eksekusi program secara asynchronous di masa depan
  // Timer bisa di schedule untuk berjalan satu kali, atau berjalan berulang kali

  
  @Test
  void delayedJob() throws InterruptedException {
    TimerTask task = new TimerTask(){
      @Override
      public void run() {
        System.out.println("Delayed Job");
      }
    };

    Timer timer = new Timer();

    // menjalankan task sesuai dengan schedule sebanyak satu kali dengan delay 2 detik
    timer.schedule(task, 2000);

    Thread.sleep(3000L);
  }


  @Test
  void periodicJob() throws InterruptedException {
    TimerTask task = new TimerTask(){
      @Override
      public void run() {
        System.out.println("Delayed Job");
      }
    };

    Timer timer = new Timer();

    // menjalankan task sesuai dengan schedule secara terus menerus dengan delay 2 detik diawal, dan selang 3 detik untuk run selanjutnya
    timer.schedule(task, 2000, 3000);

    Thread.sleep(10_000L);
  }
}
