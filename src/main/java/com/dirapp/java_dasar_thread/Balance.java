package com.dirapp.java_dasar_thread;

public class Balance {
  // Deadlock adalah kondisi dimana beberapa thread saling menunggu satu sama lain
  // karena beberapa thread tersebut melakukan lock dan menunggu lock lain dari thread lain
  // sehingga semua thread tidak berjalan, karena hanya menunggu lock
  
  private Long value;

  public Balance(Long value) {
    this.value = value;
  }

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  // simulasi ketika terjadi deadlock
  public static void transferDeadlock(Balance from, Balance to, Long value) throws InterruptedException {
    synchronized (from) {
      Thread.sleep(1000);
      synchronized (to) {
        Thread.sleep(1000);
        from.setValue(from.getValue() - value);
        to.setValue(to.getValue() + value);
      }
    }
  }

  public static void transfer(Balance from, Balance to, Long value) throws InterruptedException {
    synchronized (from) {
      Thread.sleep(1000);
      from.setValue(from.getValue() - value);
    }

    synchronized (to) {
      Thread.sleep(1000);
      to.setValue(to.getValue() + value);
    }
  }
}
