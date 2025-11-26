package async.ex01.thread.ex04;


import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// BlockingQueue 사용 (Thread-Safe)
class BlockingQueueWorker extends Thread {

    // 여러 스레드가 함께 사용하는 공유 큐
    public static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private final int count;

    public BlockingQueueWorker(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            String value = UUID.randomUUID().toString();
            // 내부적으로 동기화 처리 되어 있어 Thread-Safe
            queue.add(value);   // 또는 queue.put(value);
        }
    }
}