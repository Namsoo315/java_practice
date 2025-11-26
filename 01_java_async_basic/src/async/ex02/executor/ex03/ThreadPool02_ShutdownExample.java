package async.ex02.executor.ex03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool02_ShutdownExample {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService pool = Executors.newFixedThreadPool(2);

    for (int i = 0; i < 5; i++) {
      int taskId = i + 1;
      pool.submit(() -> {
        System.out.println("작업 ID : " + taskId + ", ThreadName : " + Thread.currentThread().getName());
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ignored) {} // 알아서 무시해줌
      });
    }

    pool.shutdown();  // shutdown은 종료 요청 및 더 이상 작업을 받아들이지 않는 명령

    if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
      System.out.println("시간 내 종료 실패, 강제 종료 시행 필요");
      pool.shutdownNow(); // 강제 종료
    }else {
      System.out.println("시간 내 정상 종료 됨");
    }
  }

}
