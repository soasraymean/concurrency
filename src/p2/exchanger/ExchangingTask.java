package p2.exchanger;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Exchanger;

public abstract class ExchangingTask implements Runnable {

    private final Exchanger<Queue<ExchangeableObject>> exchanger;
    private Queue<ExchangeableObject> queue;

    public ExchangingTask(Exchanger<Queue<ExchangeableObject>> exchanger) {
        this.exchanger = exchanger;
        queue = new ArrayDeque<>();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            handle(queue);
            exchange();
        }
    }

    protected abstract void handle(Queue<ExchangeableObject> queue);

    private void exchange() {
        try {
            queue = exchanger.exchange(queue);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
