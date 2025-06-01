package p1.atomic;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Runner {
    public static void main(String[] args) {

        EvenNumberGenerator generator = new EvenNumberGenerator();

        int taskGenerationAmount = 10_000;
        int amountOfGeneratingThreads = 5;
        Runnable task = () -> IntStream.range(0, taskGenerationAmount).forEach(i -> {
            generator.generate();
        });
        Thread[] threads = createThreads(task, amountOfGeneratingThreads);
        startThreads(threads);

        waitForThreads(threads);

        int expectedGeneratorValue = amountOfGeneratingThreads * taskGenerationAmount * EvenNumberGenerator.GENERATION_DELTA;
        int actualGeneratorValue = generator.getValue();
        if (expectedGeneratorValue != actualGeneratorValue) {
            throw new RuntimeException(
                    "Expected generator value is " + expectedGeneratorValue + " but got " + actualGeneratorValue);
        }

    }

    private static Thread[] createThreads(Runnable task, int nThreads) {
        return IntStream.range(0, nThreads)
                .mapToObj(i -> new Thread(task))
                .toArray(Thread[]::new);
    }

    private static void startThreads(Thread[] threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }

    private static void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            thread.interrupt();
        }
    }

    private static void waitForThreads(Thread[] threads) {
        Arrays.stream(threads).forEach(Runner::joinThread);
    }
}
