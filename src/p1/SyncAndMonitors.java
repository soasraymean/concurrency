package p1;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class SyncAndMonitors {

    private static int firstCounter = 0;
    private static int secondCounter = 0;

    private static final int FIRST_COUNTER_INCREMENT_OPERATIONS_AMOUNT = 500;
    private static final int SECOND_COUNTER_INCREMENT_OPERATIONS_AMOUNT = 600;

    private static final Object LOCK_N1 = new Object();
    private static final Object LOCK_N2 = new Object();

    public static void main(String[] args) {

        Thread t1 = createIncrementalThread(FIRST_COUNTER_INCREMENT_OPERATIONS_AMOUNT, i -> incrementFirstCounter());
        Thread t2 = createIncrementalThread(FIRST_COUNTER_INCREMENT_OPERATIONS_AMOUNT, i -> incrementFirstCounter());

        Thread t3 = createIncrementalThread(SECOND_COUNTER_INCREMENT_OPERATIONS_AMOUNT, i -> incrementSecondCounter());
        Thread t4 = createIncrementalThread(SECOND_COUNTER_INCREMENT_OPERATIONS_AMOUNT, i -> incrementSecondCounter());

        startThreads(t1, t2, t3, t4);
        joinThreads(t1, t2, t3, t4);

        System.out.println(firstCounter);
        System.out.println(secondCounter);

        System.out.println("------------------------------------------");

        Counter counter1 = new Counter();
        Counter counter2 = new Counter();

        Thread t21 = createIncrementalThread(FIRST_COUNTER_INCREMENT_OPERATIONS_AMOUNT, i -> counter1.increment());
        Thread t22 = createIncrementalThread(FIRST_COUNTER_INCREMENT_OPERATIONS_AMOUNT, i -> counter1.increment());

        Thread t23 = createIncrementalThread(SECOND_COUNTER_INCREMENT_OPERATIONS_AMOUNT, i -> counter2.increment());
        Thread t24 = createIncrementalThread(SECOND_COUNTER_INCREMENT_OPERATIONS_AMOUNT, i -> counter2.increment());

        startThreads(t21, t22, t23, t24);
        joinThreads(t21, t22, t23, t24);

        System.out.println(counter1.getCounter());
        System.out.println(counter2.getCounter());

    }

    private static Thread createIncrementalThread(int incrementOperationsAmount, IntConsumer operation) {
        return new Thread(() -> {
            IntStream.range(0, incrementOperationsAmount).forEach(operation);
        });
    }

    private static void startThreads(Thread... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }

    private static void joinThreads(Thread... threads) {
        Arrays.stream(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private static void incrementFirstCounter() {   //synchronized
        synchronized (LOCK_N1) {
            firstCounter++;
        }
    }

    private static void incrementSecondCounter() {  //synchronized
        synchronized (LOCK_N2) {
            secondCounter++;
        }
    }

    private static class Counter {
        private int counter;

        public synchronized void increment() {
            counter++;
//            synchronized (this) {
//                counter++;
//            }
        }

        public int getCounter() {
            return counter;
        }
    }

}
