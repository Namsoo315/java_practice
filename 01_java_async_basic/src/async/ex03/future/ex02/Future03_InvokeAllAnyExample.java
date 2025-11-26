package async.ex03.future.ex02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Future03_InvokeAllAnyExample {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService service = Executors.newFixedThreadPool(3);

    List<Callable<String>> tasks = List.of(
        () -> {Thread.sleep(300);return "작업 1 완료";},
        () -> {Thread.sleep(100);return "작업 2 완료";},
        () -> {Thread.sleep(200);return "작업 3 완료";}
    );

    // 1) invokeAll : 모든 작업 끝날때 까지 대기
    List<Future<String>> results = service.invokeAll(tasks);
    System.out.println("invoke 작업 결과");
    for (Future<String> result : results) {
      System.out.println(result.get());
    }

    System.out.println("-------------------------------------------------");

    // 2) invokeAny : 가장 먼저 끝난 작업의 결과만 받음
    String fastResult = service.invokeAny(tasks);
    System.out.println("invokeAny의 가장 빠른 결과 : " + fastResult);
    service.shutdown();


  }

}
