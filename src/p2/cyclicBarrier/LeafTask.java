package p2.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class LeafTask extends Task {

    private long secondsDuration;
    private CyclicBarrier barrier;

    public LeafTask(long id, long secondsDuration, CyclicBarrier barrier) {
        super(id);
        this.secondsDuration = secondsDuration;
        this.barrier = barrier;
    }

    @Override
    public void perform() {
        try {
            System.out.printf("%s has been started\n", this);
            TimeUnit.SECONDS.sleep(secondsDuration);
            System.out.printf("%s has been finished\n", this);
            barrier.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
