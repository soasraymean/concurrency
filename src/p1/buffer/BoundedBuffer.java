package p1.buffer;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer<T> {

    private final Lock lock;
    private final Condition notFull;
    private final Condition notEmpty;

    private final T[] buffer;
    private int size;

    public BoundedBuffer(int capacity) {
        buffer = (T[]) new Object[capacity];
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    public boolean isFull() {
        lock.lock();
        try {
            return size == buffer.length;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return size == 0;
        } finally {
            lock.unlock();
        }
    }

    public void put(T element) {
        lock.lock();
        try {
            while (isFull()) {
                notFull.await();
            }

            buffer[size++] = element;
            System.out.printf("%s was put in p1.buffer. Result p1.buffer %s%n", element, Arrays.toString(buffer));
            notEmpty.signal();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        lock.lock();
        try {
            while (isEmpty()) {
                notEmpty.await();
            }

            T t = buffer[--size];
            buffer[size] = null;
            System.out.printf("%s was taken from p1.buffer. Result p1.buffer %s%n", t, Arrays.toString(buffer));
            notFull.signal();
            return t;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "BoundedBuffer{" +
                "p1.buffer=" + Arrays.toString(buffer) +
                ", size=" + size +
                '}';
    }
}
