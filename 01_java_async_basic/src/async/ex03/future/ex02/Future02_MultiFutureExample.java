package async.ex03.future.ex02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Future02_MultiFutureExample {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(3);
    List<Future<Integer>> futures = new ArrayList<>();  // thread safe 하지 않아도 됨.

    for (int i = 1; i <= 5; i++) {
      final int count = i;
      Future<Integer> future = service.submit(() -> {
        System.out.println("작업 : " + count + "실행");
        return count * 10;
      });

      futures.add(future);
    }

    // 모든 작업의 결과 수집
    int sum = 0;
    for (Future<Integer> future : futures) {
      sum += future.get();  // 모든 작업이 끝날때 까지 대기
    }
    System.out.println("총합 : " + sum);

    service.shutdown();
  }
}
