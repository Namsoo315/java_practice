package async.ex01.thread.ex01;

public class MyThread extends Thread {

  public MyThread(String myThread01) {
    this.setName(myThread01);
  }

  @Override
  public void run() {
    System.out.println(this.getName() + "Thread 시작 ");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      System.out.println(this.getName() + " 인터럽트 발생");
    }

    System.out.println(this.getName() + " Thread 종료");
  }
}