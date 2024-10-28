# Java Thread
Thread --> proses ringan yang dijalankan pada proses aplikasi

- Synchronous/Blocking --> kode program berjalan secara sequential(berurut), semua tahapan ditunggu sampai prosesnya selesai, baru melanjutkan eksekusi tahapan selanjutnya
- Asnchronous/NonBlocking --> kode program berjalan dan tidak menunggu eksekusi kode selesai, langsung melanjutkan ke tahapan kode selanjutnya

## Parallel Programming
- Definisi: Parallel programming adalah teknik menjalankan beberapa tugas secara bersamaan pada berbagai core CPU, memungkinkan eksekusi tugas-tugas tersebut benar-benar berjalan di waktu yang sama (simultan).
Tujuan: Mempercepat eksekusi tugas dengan membaginya ke beberapa bagian yang dapat dijalankan secara paralel.
- Contoh: Prosesor dengan banyak core yang menjalankan tugas besar dengan membagi bagian-bagian tugas ke setiap core.
- Penggunaan: Sangat berguna dalam aplikasi yang memerlukan komputasi berat seperti pemrosesan data dalam jumlah besar, simulasi ilmiah, atau aplikasi machine learning.
Contoh Parallel Programming: Misalkan Anda memiliki 1 juta angka yang ingin dijumlahkan. Dengan parallel programming, Anda bisa membagi data ini menjadi beberapa bagian, dan tiap bagian dijumlahkan secara paralel oleh core yang berbeda, lalu hasilnya digabungkan.

## Concurrency Programming
- Definisi: Concurrency programming adalah teknik yang memungkinkan beberapa tugas tampak berjalan secara bersamaan dengan mengatur pengaksesan dan pengalihan di antara tugas-tugas tersebut, meskipun tidak harus berjalan di waktu yang sama.
- Tujuan: Mengelola banyak tugas secara efisien dengan membuat mereka beralih secara cepat dan meminimalkan waktu tunggu.
Contoh: Sistem operasi yang mengelola beberapa aplikasi berjalan "bersamaan" dengan bergantian mengalokasikan waktu prosesor ke tiap aplikasi.
- Penggunaan: Concurrency lebih umum pada aplikasi yang menunggu masukan/keluaran (I/O-bound), seperti server web yang harus menangani banyak permintaan atau aplikasi GUI yang merespons input pengguna.
Contoh Concurrency Programming: Misalkan ada dua tugas: membaca file besar dan menghitung sesuatu. Dengan concurrency, alih-alih menunggu seluruh file terbaca, program bisa memulai perhitungan di sela-sela membaca bagian-bagian file. Tugas-tugas ini mungkin terlihat berjalan bersamaan, tetapi sebenarnya mereka hanya bergantian.

### Perbedaan Utama
- Aspek	|| Parallel Programming	|| Concurrency Programming
- Tujuan || Meningkatkan kecepatan dengan pemrosesan simultan || Mengelola beberapa tugas dengan efisien agar tetap responsif
- Eksekusi || Tugas benar-benar berjalan secara simultan di beberapa core CPU || Tugas bergantian eksekusi (tidak harus simultan)
- Jenis Tugas || CPU-bound (komputasi intensif) || I/O-bound atau multitasking
- Contoh Penerapan || Komputasi ilmiah, Machine Learning || Web server, aplikasi GUI

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


## Running Task while Java Springboot Start Initialized
1. Menggunakan @PostConstruct
Anda dapat menulis kode yang ingin dieksekusi setelah bean diinisialisasi dengan anotasi @PostConstruct.
```
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader {
    @Autowired
    private YourRepository yourRepository;

    @PostConstruct
    public void init() {
        // Query ke database
        yourRepository.findAll().forEach(System.out::println);
    }
}
```

2. Menggunakan CommandLineRunner
CommandLineRunner adalah interface yang disediakan Spring Boot untuk menjalankan kode setelah aplikasi diinisialisasi. Anda dapat menggunakannya untuk menjalankan query saat startup.
```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseQueryRunner implements CommandLineRunner {
    @Autowired
    private YourRepository yourRepository;

    @Override
    public void run(String... args) throws Exception {
        // Query ke database
        yourRepository.findAll().forEach(System.out::println);
    }
}
```

3. Menggunakan ApplicationRunner
ApplicationRunner mirip dengan CommandLineRunner, tetapi menerima ApplicationArguments yang lebih kaya untuk mengelola argumen aplikasi.
```
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseQueryApplicationRunner implements ApplicationRunner {
    @Autowired
    private YourRepository yourRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Query ke database
        yourRepository.findAll().forEach(System.out::println);
    }
}
```


