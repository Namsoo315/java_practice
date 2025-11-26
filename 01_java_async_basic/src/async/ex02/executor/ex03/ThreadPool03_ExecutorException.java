package async.ex02.executor.ex03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool03_ExecutorException {
  public static void main(String[] args) {
    // 예외 처리 방법 - Runnable 기준
    ExecutorService service = Executors.newFixedThreadPool(1);

    // Runnable 예외처리는 내부에서 직접 try-catch문 활용!!
    service.execute(() -> {
      System.out.println("일반 작업 시행중!");
      try {
        int a = 1/0;
      } catch (ArithmeticException e) {
        System.out.println("예외 처리, " + e.getMessage());
      }
      System.out.println("예외 처리 발생 이후!");
    });
  }

}
