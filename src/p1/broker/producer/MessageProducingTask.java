package p1.broker.producer;

import p1.broker.MessageBroker;
import p1.broker.model.Message;

import java.util.concurrent.TimeUnit;

public class MessageProducingTask implements Runnable {

    private static final int SLEEP_TIME_IN_SECONDS = 1;

    private final MessageBroker messageBroker;
    private final MessageFactory messageFactory;
    private final int maximumAmountOfMessagesToProduce;
    private final String name;

    public MessageProducingTask(MessageBroker messageBroker, MessageFactory messageFactory, int maximumAmountOfMessagesToProduce, String name) {
        this.messageBroker = messageBroker;
        this.messageFactory = messageFactory;
        this.maximumAmountOfMessagesToProduce = maximumAmountOfMessagesToProduce;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message message = messageFactory.createMessage();
                TimeUnit.SECONDS.sleep(SLEEP_TIME_IN_SECONDS);
                messageBroker.produce(message, this);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getMaximumAmountOfMessagesToProduce() {
        return maximumAmountOfMessagesToProduce;
    }

    public String getName() {
        return name;
    }
}
