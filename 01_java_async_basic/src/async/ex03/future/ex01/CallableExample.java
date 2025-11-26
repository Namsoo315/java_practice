package async.ex03.future.ex01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService service = Executors.newSingleThreadExecutor();

    // 1) Callable<Integer>
    Callable<Integer> intTask = () -> {
      System.out.println("실행");
      return 10 + 20;
    };

    Future<Integer> future = service.submit(intTask);
    Integer result = future.get();  // blocking 메서드
    System.out.println("결과 : " + result);

    // 2) Callable<String><
    Callable<String> stringTask = () -> {
      System.out.println("실행");
      return "Hello Callable!";
    };
    String result2 = service.submit(stringTask).get();
    System.out.println("결과 : " + result2);

    service.shutdown();
  }

}
