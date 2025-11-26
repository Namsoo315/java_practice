package async.ex01.thread.ex03;

import java.math.BigInteger;

public class Counter01 extends Thread {
//  public static int count = 0; // 실제로는 임계영역이 되지않는 코드
  public static BigInteger count = new BigInteger("0");  // 임계영역

  @Override
  public void run() {
    for(int i = 0; i< 100; i++) {
      count = count.add(BigInteger.valueOf(1)); // 경쟁 상태

      // 일종의 트랜잭션
      // 라인 단위로 분석!
      // step01) count.add(BigInteger.valueOf(1));
      // ... 문자열 더한다거나 내부적인 계산으로 인하여 어셈블리 레벨로는 명령어 20개가 있을 예정
      // step2) count = 계산된 값!
    }
  }
}
