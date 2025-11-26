package async.ex01.thread.ex01;

public class Run {
  public static void main(String[] args) {
    System.out.println("Main Thread 시작!");

    // Thread 생성
    MyThread myThread1 = new MyThread("MyThread01");
    MyThread myThread2 = new MyThread("MyThread02");

    myThread1.start();
    myThread2.start();

    System.out.println("Main Thread 종료!");

    // 람다식 Runnable 구현 (함수형 인터페이스)
    final String name = "MyThread03";
    Runnable task = () -> {
      System.out.println(name + "Thread 시작 ");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.out.println(name + " 인터럽트 발생");
      }

      System.out.println(name + " Thread 종료");
    };

    Thread thread3 = new Thread(task);
    thread3.start();
    System.out.println("Main Thread 종료!");
  }
}
