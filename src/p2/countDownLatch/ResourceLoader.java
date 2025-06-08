package p2.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ResourceLoader extends ResourceTask{
    private long secondsDuration;

    public ResourceLoader(long id, CountDownLatch latch, long secondsDuration) {
        super(id, latch);
        this.secondsDuration = secondsDuration;
    }

    @Override
    protected void run(CountDownLatch latch) {
        try {
            System.out.printf("%s is loading resource\n", this);
            TimeUnit.SECONDS.sleep(secondsDuration);
            System.out.printf("Resource was loaded by %s\n", this);
            latch.countDown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
