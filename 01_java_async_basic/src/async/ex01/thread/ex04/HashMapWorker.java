package async.ex01.thread.ex04;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HashMapWorker extends Thread {

  public static Map<UUID, String> map = new HashMap<>();
  private final int count;

  public HashMapWorker(int count) {
    this.count = count;
  }

  @Override
  public void run() {
    for (int i = 0; i< count; i++) {
      UUID uuid = UUID.randomUUID();
      map.put(uuid, uuid.toString());
    }
  }
}
