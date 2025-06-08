package p2.countDownLatch;

import java.util.concurrent.CountDownLatch;

public class ResourceLoaderFactory extends ResourceTaskFactory {

    private long nextSecondsDuration = 1;


    @Override
    protected ResourceLoader createTask(long id, CountDownLatch latch) {
        return new ResourceLoader(id, latch, nextSecondsDuration++);
    }
}
