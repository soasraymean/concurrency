package p1.pool;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractPool<T> {

    private final List<PoolObject<T>> poolObjects;
    private final Semaphore semaphore;

    public AbstractPool(Supplier<T> objectSupplier, int size) {
        poolObjects = createPoolObjects(objectSupplier, size);
        semaphore = new Semaphore(size);
    }

    public T acquire() {
        semaphore.acquireUninterruptibly();
        return acquireObject();
    }

    public void release(T object) {
        if (releaseObject(object)) {
            semaphore.release();
        }
    }

    private synchronized boolean releaseObject(T object) {
        return poolObjects.stream()
                .filter(PoolObject::isBusy)
                .filter(poolObject -> Objects.equals(poolObject.getObject(), object))
                .findFirst()
                .map(this::cleanPoolObject)
                .isPresent();
    }

    private PoolObject<T> cleanPoolObject(PoolObject<T> poolObject) {
        poolObject.setBusy(false);
        this.cleanObject(poolObject.getObject());
        return poolObject;
    }

    protected abstract void cleanObject(T object);

    private synchronized T acquireObject() {
        return poolObjects.stream()
                .filter(poolObject -> !poolObject.isBusy())
                .findFirst()
                .map(AbstractPool::markAsBusy)
                .map(PoolObject::getObject)
                .orElseThrow(IllegalStateException::new);
    }

    private static <T> PoolObject<T> markAsBusy(PoolObject<T> poolObject) {
        poolObject.setBusy(true);
        return poolObject;
    }

    private static <T> List<PoolObject<T>> createPoolObjects(Supplier<T> objectSupplier, int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> objectSupplier.get())
                .map(object -> new PoolObject<>(object, false))
                .collect(Collectors.toList());
    }

    private static final class PoolObject<T> {
        private final T object;
        private boolean busy;

        public PoolObject(T object, boolean busy) {
            this.object = object;
            this.busy = busy;
        }

        public T getObject() {
            return object;
        }

        public boolean isBusy() {
            return busy;
        }

        public void setBusy(boolean busy) {
            this.busy = busy;
        }
    }

}
