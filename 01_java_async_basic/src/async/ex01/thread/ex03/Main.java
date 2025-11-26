package async.ex01.thread.ex03;


public class Main {
  public static void main(String[] args) throws InterruptedException {
    runCounter01();
    Thread.sleep(100);
    runCounter02();
    Thread.sleep(100);
    runCounter03();
  }

  private static void runCounter01() throws InterruptedException {
    Counter01[] threads = new Counter01[10];

    long start = System.nanoTime();

    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Counter01();
      threads[i].start();
    }
    for (Thread t : threads) {
      t.join();
    }

    long end = System.nanoTime();
    System.out.println("Counter01 최종 count = " + Counter01.count);
    System.out.println("Counter01 소요 시간(ns) = " + (end - start));
  }

  private static void runCounter02() throws InterruptedException {
    Counter02[] threads = new Counter02[10];

    long start = System.nanoTime();

    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Counter02();
      threads[i].start();
    }
    for (Thread t : threads) {
      t.join();
    }

    long end = System.nanoTime();
    System.out.println("Counter02 최종 count = " + Counter02.count);
    System.out.println("Counter02 소요 시간(ns) = " + (end - start));
  }

  private static void runCounter03() throws InterruptedException {
    Counter03[] threads = new Counter03[10];

    long start = System.nanoTime();

    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Counter03();
      threads[i].start();
    }
    for (Thread t : threads) {
      t.join();
    }

    long end = System.nanoTime();
    System.out.println("Counter03 최종 count = " + Counter03.count);
    System.out.println("Counter03 소요 시간(ns) = " + (end - start));
  }
}
