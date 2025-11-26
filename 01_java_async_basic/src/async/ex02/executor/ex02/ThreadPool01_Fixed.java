package async.ex02.executor.ex02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool01_Fixed {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2); // 스레드 2개

        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            pool.submit(() ->
                    System.out.println(
                            "FixedPool 작업 " + taskId +
                                    " 실행 스레드: " + Thread.currentThread().getName()
                    )
            );
        }

        pool.shutdown();
    }
}