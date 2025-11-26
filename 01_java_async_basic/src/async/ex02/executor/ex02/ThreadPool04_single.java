package async.ex02.executor.ex02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool04_single {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            pool.submit(() ->
                    System.out.println(
                            "SingleExecutor 작업 " + taskId +
                                    " 실행 스레드: " + Thread.currentThread().getName()
                    )
            );
        }
        pool.shutdown();
    }

}