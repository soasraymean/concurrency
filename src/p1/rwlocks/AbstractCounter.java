package p1.rwlocks;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public abstract class AbstractCounter {
    private long value;

    protected abstract Lock getReadLock();

    protected abstract Lock getWriteLock();

    public OptionalLong getValue() {
        Lock lock = getReadLock();
        lock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
            return OptionalLong.of(value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return OptionalLong.empty();
        } finally {
            lock.unlock();
        }
    }

    public void increment() {
        Lock lock = getWriteLock();
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }
}
