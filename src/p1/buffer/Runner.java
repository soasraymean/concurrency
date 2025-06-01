package p1.buffer;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Runner {
    public static void main(String[] args) {
        BoundedBuffer<Integer> intBuffer = new BoundedBuffer<>(5);

        Runnable intProducingTask = () -> Stream.iterate(0, i -> i + 1).forEach(i -> {
            try {
                intBuffer.put(i);
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
        Thread producingThread = new Thread(intProducingTask);

        Runnable intConsumingTask = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    intBuffer.get();
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        Thread consumingThread = new Thread(intConsumingTask);

        producingThread.start();
        consumingThread.start();

    }
}
