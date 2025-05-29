package p1.broker.consumer;

import p1.broker.MessageBroker;
import p1.broker.exception.MessageConsumingException;

import java.util.concurrent.TimeUnit;

public class MessageConsumingTask implements Runnable {

    private static final int SLEEP_TIME_IN_SECONDS = 3;

    private final int minimalAmountOfMessagesToConsume;
    private final MessageBroker messageBroker;
    private final String name;

    public MessageConsumingTask(MessageBroker messageBroker, int minimalAmountOfMessagesToConsume, String name) {
        this.messageBroker = messageBroker;
        this.minimalAmountOfMessagesToConsume = minimalAmountOfMessagesToConsume;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.SECONDS.sleep(SLEEP_TIME_IN_SECONDS);
                messageBroker.consume(this).orElseThrow(MessageConsumingException::new);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getMinimalAmountOfMessagesToConsume() {
        return minimalAmountOfMessagesToConsume;
    }

    public String getName() {
        return name;
    }
}
