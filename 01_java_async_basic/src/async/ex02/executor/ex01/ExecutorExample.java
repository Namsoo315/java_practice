package async.ex02.executor.ex01;

import java.util.concurrent.Executor;

public class ExecutorExample {

  public static void main(String[] args) {
    Executor executor = new Executor() {

      @Override
      public void execute(Runnable command) {
        System.out.println("새로운 스레드 생성!");
        new Thread(command).start();  // 동시에 2개로 만들기
        new Thread(command).start();
      }
    };

    Executor executor2 = command -> {
      System.out.println("새로운 스레드 생성!!");
      new Thread(command).start();
    };

    executor.execute(new Runnable() {
      @Override
      public void run() {
        System.out.println("사용자 작업 실행!");
      }
    });

    executor2.execute(() -> System.out.println("사용자 작업 실행!!"));
  }

}
