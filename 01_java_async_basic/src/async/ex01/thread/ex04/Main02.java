package async.ex01.thread.ex04;

public class Main02 {

    private static final int NUM_THREADS = 50;
    private static final int INSERT_PER_THREAD = 100;

    public static void main(String[] args) throws InterruptedException {
        runLinkedListTest();
        runBlockingQueueTest();
    }

    private static void runLinkedListTest() throws InterruptedException {
        LinkedListWorker[] threads = new LinkedListWorker[NUM_THREADS];

        long start = System.nanoTime();

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new LinkedListWorker(INSERT_PER_THREAD);
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }

        long end = System.nanoTime();
        long expected = (long) NUM_THREADS * INSERT_PER_THREAD;

        System.out.println("=== LinkedList (not thread-safe) ===");
        System.out.println("기대 size = " + expected);
        System.out.println("실제 size = " + LinkedListWorker.list.size());
        System.out.println("소요 시간(ns) = " + (end - start));
        System.out.println();
    }

    private static void runBlockingQueueTest() throws InterruptedException {
        BlockingQueueWorker[] threads = new BlockingQueueWorker[NUM_THREADS];

        long start = System.nanoTime();

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new BlockingQueueWorker(INSERT_PER_THREAD);
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }

        long end = System.nanoTime();
        long expected = (long) NUM_THREADS * INSERT_PER_THREAD;

        System.out.println("=== BlockingQueue (LinkedBlockingQueue, thread-safe) ===");
        System.out.println("기대 size = " + expected);
        System.out.println("실제 size = " + BlockingQueueWorker.queue.size());
        System.out.println("소요 시간(ns) = " + (end - start));
        System.out.println();
    }
}
