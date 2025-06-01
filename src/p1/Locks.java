package p1;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Locks {
    public static void main(String[] args) {

        EvenNumberGenerator generator = new EvenNumberGenerator();
        Runnable generatingTask = () -> IntStream.range(0, 100).forEach(i -> System.out.println(generator.generateNext()));

        Thread thread1 = new Thread(generatingTask);
        Thread thread2 = new Thread(generatingTask);
        Thread thread3 = new Thread(generatingTask);

        thread1.start();
        thread2.start();
        thread3.start();

//        Counter counter = new Counter();
//        Thread incrementingThread = new Thread(createTaskForCounter(counter, i -> counter.increment(), 10));
//        Thread decrementingThread = new Thread(createTaskForCounter(counter, i -> counter.decrement(), 10));
//
//        startThreads(incrementingThread, decrementingThread);
//        joinThreads(incrementingThread, decrementingThread);
//
//        System.out.println(counter.getValue());

    }

    private static void startThreads(Thread... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }

    private static void joinThreads(Thread... threads) {
        Arrays.stream(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        });
    }

    private static Runnable createTaskForCounter(Counter counter, IntConsumer action, int times) {
        return () -> {
            counter.lock();
            try {
                IntStream.range(0, times).forEach(action);
            } finally {
                counter.unlock();
            }
        };
    }

    private static class Counter {
        private final Lock lock = new ReentrantLock();
        private int value;

        public void lock() {
            lock.lock();
            printCurrentThreadName("Thread '%s' locked counter.\n");
        }

        public void unlock() {
            printCurrentThreadName("Thread '%s' is unlocking counter.\n");
            lock.unlock();
        }

        public void increment() {
            value++;
            printCurrentThreadName("Thread '%s' incremented counter.\n");
        }

        public void decrement() {
            value--;
            printCurrentThreadName("Thread '%s' decremented counter.\n");
        }

        private void printCurrentThreadName(String msg) {
            System.out.printf(msg, Thread.currentThread().getName());
        }

        public int getValue() {
            return value;
        }
    }

    private static class EvenNumberGenerator {

        private final Lock lock;

        private int previousGenerated;

        public EvenNumberGenerator() {
            lock = new ReentrantLock();
            previousGenerated = -2;
        }

        public int generateNext() {
//            lock.lock();
//            try {
//                return previousGenerated += 2;
//            } finally {
//                lock.unlock();
//            }

            return lock.tryLock() ? onSuccessAcquireLock() : onFailureAcquireLock();
        }

        private int onSuccessAcquireLock() {
            try {
                return previousGenerated+=2;
            } finally {
                lock.unlock();
            }
        }

        private int onFailureAcquireLock() {
            System.out.printf("Thread '%s' didn't acquire lock.\n", Thread.currentThread().getName());
            throw new RuntimeException();
        }
    }
}
