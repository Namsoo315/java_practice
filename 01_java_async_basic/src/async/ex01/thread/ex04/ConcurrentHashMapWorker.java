package async.ex01.thread.ex04;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWorker extends Thread {

  public static Map<UUID, String> map = new ConcurrentHashMap<>();
  private final int count;

  public ConcurrentHashMapWorker(int count) {
    this.count = count;
  }

  @Override
  public void run() {
    for (int i = 0; i < count; i++) {
      UUID uuid = UUID.randomUUID();
      map.put(uuid, uuid.toString());
    }
  }
}