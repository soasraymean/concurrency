package p1.rwlocks;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Runner {
    public static void main(String[] args) throws InterruptedException {

//        testCounter(CounterWithLock::new); //218903578 //54

        testCounter(CounterWithRWLock::new); //29835074 //250

    }

    private static void testCounter(Supplier<? extends AbstractCounter> counterFactory) throws InterruptedException {
        AbstractCounter counter = counterFactory.get();
        int amountOfThreadsReadingValue = 50;
        ReadValueTask[] readValueTasks = createReadingTasks(counter, amountOfThreadsReadingValue);
        Thread[] readingThreads = mapToThreads(readValueTasks);

        Runnable writeValueTask = createWritingTask(counter);
        int amountOfThreadsWritingValue = 2;
        Thread[] writingThreads = createThreads(writeValueTask, amountOfThreadsWritingValue);

        startThreads(readingThreads);
        startThreads(writingThreads);

        TimeUnit.SECONDS.sleep(5);

        interruptThreads(readingThreads);
        interruptThreads(writingThreads);

        waitUntilFinished(readingThreads);
        long totalReads = getTotalReads(readValueTasks);
        System.out.println(totalReads);

    }

    private static ReadValueTask[] createReadingTasks(AbstractCounter counter, int amountOfTasks) {
        return IntStream.range(0, amountOfTasks)
                .mapToObj(i -> new ReadValueTask(counter))
                .toArray(ReadValueTask[]::new);
    }

    private static Thread[] mapToThreads(Runnable[] tasks) {
        return Arrays.stream(tasks)
                .map(Thread::new)
                .toArray(Thread[]::new);
    }

    private static Runnable createWritingTask(AbstractCounter counter) {
        return () -> {
            while (!Thread.currentThread().isInterrupted()) {
                counter.increment();
            }
        };
    }

    private static Thread[] createThreads(Runnable task, int amountOfThreads) {
        return IntStream.range(0, amountOfThreads)
                .mapToObj(i -> new Thread(task))
                .toArray(Thread[]::new);
    }

    private static void forEach(Thread[] threads, Consumer<Thread> action) {
        Arrays.stream(threads)
                .forEach(action);
    }

    private static void startThreads(Thread[] threads) {
        forEach(threads, Thread::start);
    }

    private static void interruptThreads(Thread[] threads) {
        forEach(threads, Thread::interrupt);
    }

    private static void waitUntilFinished(Thread[] threads) {
        forEach(threads, Runner::waitUntilFinished);
    }

    private static void waitUntilFinished(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static long getTotalReads(ReadValueTask[] readValueTasks) {
        return Arrays.stream(readValueTasks)
                .mapToLong(ReadValueTask::getAmountOfReads)
                .sum();
    }

    private static void incrementCounter(AbstractCounter counter) {
        try {
            counter.increment();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static class ReadValueTask implements Runnable {
        private final AbstractCounter counter;
        private long amountOfReads;

        public ReadValueTask(AbstractCounter counter) {
            this.counter = counter;
        }

        public long getAmountOfReads() {
            return amountOfReads;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                counter.getValue();
                amountOfReads++;
            }
        }
    }

}
