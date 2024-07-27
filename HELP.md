# Java Thread
Thread --> proses ringan yang dijalankan pada proses aplikasi

- Concurency --> mengerjakan beberapa pekerjaan satu persatu pada satu waktu
- Parallel --> mengerjakan beberapa pekerjaan sekaligus dalam satu waktu

- Synchronous/Blocking --> kode program berjalan secara sequential(berurut), semua tahapan ditunggu sampai prosesnya selesai, baru melanjutkan eksekusi tahapan selanjutnya
- Asnchronous/NonBlocking --> kode program berjalan dan tidak menunggu eksekusi kode selesai, langsung melanjutkan ke tahapan kode selanjutnya

## Learning
- test/ThreadTest.java
- main/DeamonApp.java
- main/Counter.java
- test/RaceConditionTest.java
- main/SynchronizedCounter.java
- test/SynchronizationTest.java
- main/Balance.java
- test/DeadlockTest.java
- test/ThreadCommunicationTest.java
- test/TimerTest.java

### High Level API
Untuk mempermudah penggunaan multithread
- test/ThreadPoolTest.java
- test/ExecutorServiceTest.java
- test/FutureTest.java
- test/CompletableFutureTest.java
- test/CompletionServiceTest.java
- test/ScheduledExecutorServiceTest.java
- test/AtomicTest.java
- test/LockTest.java

### Synchronizer
Pada package Concurrent terdapat banyak class untuk melakukan synchronizer, ini merupakan improvement dari locks, namun digunakan pada kasus-kasus terentu.
class-class Synchronizer banyak menggunakan locks, namun tidak perlu melakukannya secara manual, karena sudha diatur secara otomatis oleh class-class nya sendiri.
- test/SemaphoreTest.java
- test/CountDownLatchTest.java
- test/CyclicBarrierTest.java
- test/PharserTest.java
- test/ExchangerTest.java

- test/BlockingQueueTest.java
- test/ConcurrentMapTest.java
- test/ThreadLocalTest.java
- test/ThreadLocalRandomTest.java
- test/ParallelStreamTest.java
- test/ReactiveStreamTest.java