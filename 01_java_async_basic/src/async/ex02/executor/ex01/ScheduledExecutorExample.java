package async.ex02.executor.ex01;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorExample {

  public static void main(String[] args) {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // 1) 2초후 단발성 실행
    scheduler.schedule(() -> {
      System.out.println("2초후 단발성 실행");
    }, 2, TimeUnit.SECONDS);

    // 2) 1초 후 시작해서 3초 마다 반복 실행
    scheduler.scheduleAtFixedRate(() -> {
      System.out.println(" 1초 뒤 부터 주기적으로 실행 (3초)");
    }, 1, 3, TimeUnit.SECONDS);

    // 3) 10초 후 스케줄러 종료
    scheduler.schedule(() ->{
      System.out.println("10초 뒤 스케줄러 실행 종료");
      scheduler.shutdown();
    }, 10, TimeUnit.SECONDS);
  }
}
