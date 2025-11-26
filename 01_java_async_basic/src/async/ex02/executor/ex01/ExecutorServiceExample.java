package async.ex02.executor.ex01;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExample {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // ExecutorService : Executor를 기반으로 만든 구현체, 고정크기, 스케줄 등등의 기능을 지원

    // 고정 크기 pool, 풀 사이즈 : 2
    ExecutorService service = Executors.newFixedThreadPool(2);

    // Runnable 제출
    // execute : 단순 실행 메서드, 결과 값을 받을 필요가 없을 때 사용.
    service.execute(() -> {
      System.out.println("실행 Thread : "+ Thread.currentThread().getName());
      System.out.println("Runnable 작업 실행 ");
    });

    // Callable 제출 하여 결과를 Future로 변환
    // submit : 최종 결과값을 받을 필요가 있을 경우 사용.
    Future<Integer> future = service.submit(() -> {
      System.out.println("실행 Thread : "+ Thread.currentThread().getName());
      System.out.println("Callable 실행!");
      return 123;
    });

    // future 출력부
    Integer value = future.get();
    System.out.println("Callable 결과 : " + value);

    // 종료 명령
    service.shutdown();
  }
}
