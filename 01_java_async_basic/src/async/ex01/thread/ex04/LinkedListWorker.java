package async.ex01.thread.ex04;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

// LinkedList 사용 (Thread-Safe 아님)
class LinkedListWorker extends Thread {
    // 여러 스레드가 함께 사용하는 공유 리스트
    public static List<String> list = new ArrayList<>();

    private final int count;

    public LinkedListWorker(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            String value = UUID.randomUUID().toString();
            // 동기화 없이 add → 경쟁 상태 발생 가능
            list.add(value);
        }
    }
}
