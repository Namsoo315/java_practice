package async.ex02.executor.ex03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool01_SizeExample {
  public static void main(String[] args) {
    int cores = Runtime.getRuntime().availableProcessors();
    System.out.println("CPU 코어 수 : " + cores);

    // CPU 바운드 작업용 스레드 풀
    ExecutorService cpuBoundPool = Executors.newFixedThreadPool(cores);
//    ExecutorService ioBoundPool = Executors.newFixedThreadPool(2);    // I/O 처리 대략적으로 두개정도?

    for (int i = 0; i < 100; i++){
      final int count = i;
      cpuBoundPool.execute(() -> {
        System.out.println("CPU Pool 작업 실행("+ count + ") - 스레드 : " + Thread.currentThread().getName());
      });
    }

    cpuBoundPool.shutdown();
  }

}
