package com.dirapp.java_dasar_thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ReactiveStreamTest {
  // ReactiveStream diperkenalkan sejak java 9, standard baru untuk Asynchronous Stream Proccessing
  // data yang mengalir pada stream berjalan secara Asynchronous
  // ReactiveStream dikenal dengan istilah Flow (aliran data), artinya ada yang mengirim data dan ada yang menerima data
  // pihak yang mengirim data --> Publisher
  // pihak yang menerima data --> Subscriber
  

  @Test
  void publish() throws InterruptedException {
    // untuk publisher tidak perlu implementasi manual, sudah ada SubmissionPublisher<T>
    SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
    // untuk subscriber harus implementasi manual, membuat class
    PrintSubscriber subscriber1 = new PrintSubscriber("A", 1000L);
    PrintSubscriber subscriber2 = new PrintSubscriber("B", 500L);
    publisher.subscribe(subscriber1);
    publisher.subscribe(subscriber2);

    ExecutorService executor = Executors.newFixedThreadPool(10);
    executor.execute(() -> {
      for (int i = 0; i < 100; i++) {
        // melakukan publish data, data yang telah dipublish bisa diconsume oleh masing masing subscriber sesuai kebutuhannya
        publisher.submit("DATA-" + i);
        System.out.println(Thread.currentThread().getName() + " : Send DATA-" + i);
      }
    });

    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.DAYS);

    Thread.sleep(100 * 1000);
  }


  // ketika publisher mengirim data terlalu cepat, maka secara default data akan dibuffer (mirip seperti antrian)
  // secara default kapasitas buffer adalah 256 data --> SubmissionPublisher<>(Executor, bufferSize)
  // jika sudah penuh maka publisher harus menunggu sampai data diambil oleh subscriber
  @Test
  void buffer() throws InterruptedException {
    SubmissionPublisher<String> publisher = new SubmissionPublisher<>(Executors.newWorkStealingPool(), 10);
    PrintSubscriber subscriber1 = new PrintSubscriber("A", 1000L);
    PrintSubscriber subscriber2 = new PrintSubscriber("B", 500L);
    publisher.subscribe(subscriber1);
    publisher.subscribe(subscriber2);

    ExecutorService executor = Executors.newFixedThreadPool(10);
    executor.execute(() -> {
      for (int i = 0; i < 100; i++) {
        publisher.submit("DATA-" + i);
        System.out.println(Thread.currentThread().getName() + " : Send DATA-" + i);
      }
    });

    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.DAYS);

    Thread.sleep(100 * 1000);
  }


  // Processor adalah gabungan antara publisher dan juga subscriber
  // bisa menerima data dari publisher lain, dan bisa mengirim data ke subscriber lain
  @Test
  void processor() throws InterruptedException {
    SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

    HelloProcessor processor = new HelloProcessor();
    publisher.subscribe(processor);

    PrintSubscriber subscriber = new PrintSubscriber("A", 1000L);
    processor.subscribe(subscriber);

    ExecutorService executor = Executors.newFixedThreadPool(10);
    executor.execute(() -> {
      for (int i = 0; i < 100; i++) {
        publisher.submit("DATA-" + i);
        System.out.println(Thread.currentThread().getName() + " : Send DATA-" + i);
      }
    });

    Thread.sleep(100 * 1000);
  }


  public static class PrintSubscriber implements Flow.Subscriber<String> {
    private Flow.Subscription subscription;

    private String name;

    private Long sleep;

    public PrintSubscriber(String name, Long sleep) {
      this.name = name;
      this.sleep = sleep;
    }

    // ketika pertama kali menjalankan flow subscription
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
      this.subscription = subscription;
      // mengambil data 1, kemudian data akan masuk ke onNext()
      this.subscription.request(1);
    }

    // ketika menerima data
    @Override
    public void onNext(String item) {
      try {
        Thread.sleep(sleep);
        System.out.println(Thread.currentThread().getName() + " : " + name + " : " + item);
        // mengambil data 1, kemudian data akan masuk lagi ke onNext() sampai data habis
        this.subscription.request(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // ketika menerima error
    @Override
    public void onError(Throwable throwable) {
      throwable.printStackTrace();
    }

    // ketika selesai menerima data
    @Override
    public void onComplete() {
      System.out.println(Thread.currentThread().getName() + " : DONE");
    }
  }


  public static class HelloProcessor extends SubmissionPublisher<String> implements Flow.Processor<String, String> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
      this.subscription = subscription;
      this.subscription.request(1);
    }

    @Override
    public void onNext(String item) {
      String value = "Hello " + item;
      // mengirim data ke subscriber
      submit(value);
      this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
      throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
      close();
    }
  }
}
