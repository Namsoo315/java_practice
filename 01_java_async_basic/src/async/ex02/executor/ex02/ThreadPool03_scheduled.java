package async.ex02.executor.ex02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPool03_scheduled {
    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // 1) 3초 후 한 번 실행
        scheduler.schedule(
                () -> System.out.println("3초 후 한 번 실행"),
                3,
                TimeUnit.SECONDS
        );

        // 2) 1초 후 시작해서 2초마다 반복 실행
        scheduler.scheduleAtFixedRate(
                () -> System.out.println("2초마다 반복 실행"),
                1,          // initial delay
                2,          // period
                TimeUnit.SECONDS
        );

        // 10초 뒤 종료
        scheduler.schedule(() -> {
            System.out.println("스케줄러 종료");
            scheduler.shutdown();
        }, 10, TimeUnit.SECONDS);
    }
}
