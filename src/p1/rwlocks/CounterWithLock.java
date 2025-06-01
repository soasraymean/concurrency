package p1.rwlocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterWithLock extends AbstractCounter {

    private Lock lock = new ReentrantLock();

    @Override
    protected Lock getReadLock() {
        return lock;
    }

    @Override
    protected Lock getWriteLock() {
        return lock;
    }
}
