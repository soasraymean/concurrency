package p2.countDownLatch;

import java.util.concurrent.CountDownLatch;

public abstract class ResourceTaskFactory {
    private long nextId;

    public ResourceTask create(CountDownLatch latch) {
        return this.createTask(nextId++, latch );
    }

    protected abstract ResourceTask createTask(long id, CountDownLatch latch);
}
