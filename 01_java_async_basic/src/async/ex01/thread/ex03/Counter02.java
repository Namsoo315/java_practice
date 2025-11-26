package async.ex01.thread.ex03;


import java.math.BigInteger;

// synchronized 사용 - 메서드 동기화
public class Counter02 extends Thread {
  public static BigInteger count = new BigInteger("0"); // 임계영역

//   // static 변수를 일반 메서드로 접근해서 thread-safe하지 않게됨!
//    private synchronized void increment() {
//        count = count.add(BigInteger.ONE);
//    }

  private static synchronized void increment() {
    count = count.add(BigInteger.ONE);
  }

  @Override
  public void run() {
    for(int i = 0; i < 100; i++) {
      increment();
    }
  }
}
