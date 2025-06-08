package p2.countDownLatch;

import java.util.concurrent.CountDownLatch;

public abstract class ResourceTask implements Runnable{

    private long id;
    private CountDownLatch latch;

    public ResourceTask(long id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    protected abstract void run(CountDownLatch latch);

    @Override
    public void run() {
        run(latch);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}
