package async.ex02.executor.ex02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool02_cached {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            pool.submit(() ->
                    System.out.println(
                            "CachedPool 작업 " + taskId +
                                    " 실행 스레드: " + Thread.currentThread().getName()
                    )
            );
        }

        pool.shutdown();
    }
}
