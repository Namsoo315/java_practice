package async.ex01.thread.ex03;


import java.math.BigInteger;

// synchronized 사용 - 블록 동기화
public class Counter03 extends Thread {
    public static BigInteger count = new BigInteger("0"); // 임계영역

    @Override
    public void run() {
        for(int i = 0; i < 100; i++) {
            synchronized (Counter03.class) {   // 핵심은 여기로 들어갈 때 모든 스레드에서 접근 가능해야함.
                count = count.add(BigInteger.ONE); // 경쟁상태
            }
        }
    }
}