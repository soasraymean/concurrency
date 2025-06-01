package p1.rwlocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CounterWithRWLock extends AbstractCounter {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    @Override
    protected Lock getReadLock() {
        return readLock;
    }

    @Override
    protected Lock getWriteLock() {
        return writeLock;
    }
}
