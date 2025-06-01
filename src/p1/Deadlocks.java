package p1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlocks {
    public static void main(String[] args) {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        Thread t1 = new Thread(new Task(lock1, lock2));
        Thread t2 = new Thread(new Task(lock2, lock1)); // DEADLOCK - FOLLOW THE ORDER OF ARGUMENTS (LOCKS) TO PREVENT (lock1, lock2)

        t1.start();
        t2.start();
    }

    private static class Task implements Runnable {

        private static final String THREAD_IS_TRYING_TO_ACQUIRE_LOCK_MSG = "Thread '%s' is trying to acquire lock '%s'.\n";
        private static final String THREAD_ACQUIRED_LOCK_MSG = "Thread '%s' acquired lock '%s'.\n";
        private static final String THREAD_RELEASED_LOCK_MSG = "Thread '%s' released lock '%s'.\n";

        private static final String LOCK1_NAME = "LOCK_1";
        private static final String LOCK2_NAME = "LOCK_2";

        private final Lock lock1;
        private final Lock lock2;

        public Task(Lock lock1, Lock lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            String currentThreadName = Thread.currentThread().getName();

            System.out.printf(THREAD_IS_TRYING_TO_ACQUIRE_LOCK_MSG, currentThreadName, LOCK1_NAME);
            lock1.lock();
            try {
                System.out.printf(THREAD_ACQUIRED_LOCK_MSG, currentThreadName, LOCK1_NAME);
                TimeUnit.MILLISECONDS.sleep(200);

                System.out.printf(THREAD_IS_TRYING_TO_ACQUIRE_LOCK_MSG, currentThreadName, LOCK2_NAME);
                lock2.lock();
                try {
                    System.out.printf(THREAD_ACQUIRED_LOCK_MSG, currentThreadName, LOCK2_NAME);
                } finally {
                    lock2.unlock();
                    System.out.printf(THREAD_RELEASED_LOCK_MSG, currentThreadName, LOCK2_NAME);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock1.unlock();
                System.out.printf(THREAD_RELEASED_LOCK_MSG, currentThreadName, LOCK1_NAME);
            }

        }
    }
}
