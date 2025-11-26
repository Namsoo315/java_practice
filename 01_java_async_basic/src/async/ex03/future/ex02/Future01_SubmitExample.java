package async.ex03.future.ex02;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Future01_SubmitExample {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
      ExecutorService service = Executors.newFixedThreadPool(2);

      // 1) Runnable 제출 (결과값 없음)
    service.execute(() -> {
      System.out.println("Runnable 작업1 실행!");
    });

    Future<?> future1 = service.submit(() -> {
      System.out.println("Runnable 작업2 실행!");
      // Future<?> submit(Runnable) 형태로 활용가능, 리턴 값은 없으나 Future 활용 가능!
    });
    // Runnable의 future null을 가지고 있으나 timeout이나 헬프 메서드 활용 가능!

    // 2) callable 제출 (결과값이 있음)
    Future<Integer> future2 = service.submit(() -> {
      System.out.println("Callable 작업 실행!");
      return 10 + 20;
    });

    // get() 호출로 작업 완료까지 대기 및 결과 확인 가능!
    future1.get();  // null 반환하기 땜누에 결과 값 필요 없음!
    Integer result = future2.get();
    System.out.println("결과 : " + result);
    service.shutdown();
  }

}
