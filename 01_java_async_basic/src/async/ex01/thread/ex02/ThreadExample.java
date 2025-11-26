package async.ex01.thread.ex02;

public class ThreadExample {
  public static void main(String[] args) throws InterruptedException {
    // Runnable 람다식 구현
    Runnable task = () -> {
      System.out.println("Thread 시작 ");
      try {
        // 루프 작업
        for (int i = 0; i <= 5; i++) {
          if (Thread.currentThread().isInterrupted()) { // pooling 방식으로 감지
            System.out.println("인터럽트 감지!");
            break;
          }
          System.out.println("작업 중 ... Step : " + i);
          Thread.sleep(500);
        }
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println("인터럽트 발생");
        Thread.currentThread().interrupt(); //인터럽트 플래그 초기화
      }
      System.out.println("Thread 종료");
    };

    Thread thread = new Thread(task);
    // 새로운 Thread 생성하는 메서드
    thread.start();

    // 메인 스레드 sleep(1000)
    Thread.sleep(1000);
    System.out.println("메인 스레드 1000ms 대기 완료!");
    thread.interrupt();

    // 3) join -> 스레드 종료까지 기다리는 메서드
    thread.join();
    System.out.println("메인 스레드 종료");
  }
}
