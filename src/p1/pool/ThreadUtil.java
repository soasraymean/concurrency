package p1.pool;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ThreadUtil {

    public static Thread[] createThreads(Runnable task, int amountOfThreads) {
        return IntStream.range(0, amountOfThreads)
                .mapToObj(i -> new Thread(task))
                .toArray(Thread[]::new);
    }

    public static void startThreads(Thread[] threads) {
        Arrays.stream(threads)
                .forEach(Thread::start);
    }

    private ThreadUtil() {
        throw new UnsupportedOperationException();
    }

}
