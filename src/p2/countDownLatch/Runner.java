package p2.countDownLatch;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Runner {

    public static void main(String[] args) {

        int resourcesCount = 3;
        CountDownLatch latch = new CountDownLatch(resourcesCount);

        ResourceLoaderFactory resourceLoaderFactory = new ResourceLoaderFactory();
        Thread[] loadingThreads = createResourceThreads(resourceLoaderFactory, resourcesCount, latch);

        int handlingThreadsCount = 4;
        ResourceHandlerFactory resourceHandlerFactory = new ResourceHandlerFactory();
        Thread[] handlingThreads = createResourceThreads(resourceHandlerFactory, handlingThreadsCount, latch);

        Arrays.stream(loadingThreads).forEach(Thread::start);
        Arrays.stream(handlingThreads).forEach(Thread::start);


    }

    private static Thread[] createResourceThreads(ResourceTaskFactory resourceTaskFactory, int nThreads, CountDownLatch latch) {
        return IntStream.range(0, nThreads)
                .mapToObj(i -> resourceTaskFactory.create(latch))
                .map(Thread::new)
                .toArray(Thread[]::new);
    }

}
