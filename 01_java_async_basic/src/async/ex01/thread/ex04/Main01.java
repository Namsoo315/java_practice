package async.ex01.thread.ex04;

// Main
public class Main01 {

    private static final int NUM_THREADS = 10;
    private static final int INSERT_PER_THREAD = 1000;

    public static void main(String[] args) throws InterruptedException {
        runHashMapTest();
        runConcurrentHashMapTest();
    }

    private static void runHashMapTest() throws InterruptedException {
        HashMapWorker[] threads = new HashMapWorker[NUM_THREADS];

        long start = System.nanoTime();

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new HashMapWorker(INSERT_PER_THREAD);
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }

        long end = System.nanoTime();
        long expected = (long) NUM_THREADS * INSERT_PER_THREAD;

        System.out.println("=== HashMap (not thread-safe) ===");
        System.out.println("기대 size = " + expected);
        System.out.println("실제 size = " + HashMapWorker.map.size());
        System.out.println("소요 시간(ns) = " + (end - start));
        System.out.println();
    }

    private static void runConcurrentHashMapTest() throws InterruptedException {
        ConcurrentHashMapWorker[] threads = new ConcurrentHashMapWorker[NUM_THREADS];

        long start = System.nanoTime();

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new ConcurrentHashMapWorker(INSERT_PER_THREAD);
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }

        long end = System.nanoTime();
        long expected = (long) NUM_THREADS * INSERT_PER_THREAD;

        System.out.println("=== ConcurrentHashMap (thread-safe) ===");
        System.out.println("기대 size = " + expected);
        System.out.println("실제 size = " + ConcurrentHashMapWorker.map.size());
        System.out.println("소요 시간(ns) = " + (end - start));
        System.out.println();
    }
}