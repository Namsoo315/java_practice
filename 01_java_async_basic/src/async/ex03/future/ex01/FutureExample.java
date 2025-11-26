package async.ex03.future.ex01;

import java.util.concurrent.*;

public class FutureExample {

    public static void main(String[] args) throws Exception {
        System.out.println("==== 기본 Future 예제 ====");
        basicFutureExample();

        System.out.println("\n==== 2) get() 블로킹 예제 ====");
        blockingGetExample();

        System.out.println("\n==== 3) get(timeout) 시간 초과 예제 ====");
        timeoutExample();

        System.out.println("\n==== 4) cancel() 작업 취소 예제 ====");
        cancelExample();
    }


    // 1) Future 기본 사용
    private static void basicFutureExample() throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<Integer> future = service.submit(() -> 20 + 10);
        System.out.println("비동기 작업 제출 완료!");

        Integer result = future.get();  // 블로킹
        System.out.println("결과 : " + result);

        service.shutdown();
    }

    // 2) get() 블로킹 예제
    private static void blockingGetExample() throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<String> future = service.submit(() -> {
            Thread.sleep(1000);
            return "완료";
        });

        System.out.println("get() 호출 전");
        String value = future.get();// 블로킹
        System.out.println("get 호출 완료, 결과 값 : " + value);

        service.shutdown();
    }


    // 3) get(timeout) 시간 제한 예제
    private static void timeoutExample() throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<String> future = service.submit(() -> {
            Thread.sleep(2000);
            return "완료";
        });

        // 시간제한 방법

      try {
          System.out.println("submit 실행 완료!, 1초만 대기 이후 결과값 볼 예정");
          String value = future.get(1, TimeUnit.SECONDS);
          System.out.println("결과 : " + value);
      } catch (TimeoutException e) {
          System.out.println("예외 발생 (타임 아웃)");
      }
        service.shutdown();

    }


    // 4) cancel(true) 취소 예제
    private static void cancelExample() throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<?> future = service.submit(() -> {
            while (true) {
                // 무한 작업 수행 ...

                System.out.print(".");
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("인터럽트 발생!!");
                    break;
                }
            }
        });

        Thread.sleep(100);
        System.out.println("Cancel(true) 호출");
        boolean result = future.cancel(true);// 인터럽트 발생함!!
        System.out.println("취소 여부 : " + result);
        service.shutdown();
    }
}
