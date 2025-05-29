package p1.broker;

import p1.broker.consumer.MessageConsumingTask;
import p1.broker.model.Message;
import p1.broker.producer.MessageProducingTask;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class MessageBroker {

    private static final String MESSAGE_WAS_PRODUCED_TEMPLATE = "Message '%s' was produced by '%s'. Total messages: %d\n";
    private static final String MESSAGE_WAS_CONSUMED_TEMPLATE = "Message '%s' was consumed by '%s'. Total messages: %d\n";
    private static final String CURRENT_AMOUNT_OF_MESSAGES_TEMPLATE = "Total messages: %d\n";

    private final Queue<Message> messagesToConsume;
    private final int maxStoredMessages;

    MessageBroker(int maxStoredMessages) {
        messagesToConsume = new ArrayDeque<>(maxStoredMessages);
        this.maxStoredMessages = maxStoredMessages;
    }

    public synchronized void produce(Message message, MessageProducingTask task) {
        try {
            while (!shouldProduce(task)) {
                super.wait();
            }
            messagesToConsume.add(message);
//            System.out.printf(CURRENT_AMOUNT_OF_MESSAGES_TEMPLATE, messagesToConsume.size() - 1);
            System.out.printf(MESSAGE_WAS_PRODUCED_TEMPLATE, message, task.getName(), messagesToConsume.size());
            notify();


        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Optional<Message> consume(MessageConsumingTask task) {
        try {
            while (!shouldConsume(task)) {
                wait();
            }
            Message polled = messagesToConsume.poll();
//            System.out.printf(CURRENT_AMOUNT_OF_MESSAGES_TEMPLATE, messagesToConsume.size() + 1);
            System.out.printf(MESSAGE_WAS_CONSUMED_TEMPLATE, polled, task.getName(), messagesToConsume.size());
            notify();
            return Optional.ofNullable(polled);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    private boolean shouldConsume(MessageConsumingTask task) {
        return !messagesToConsume.isEmpty()
                && messagesToConsume.size() >= task.getMinimalAmountOfMessagesToConsume();
    }

    private boolean shouldProduce(MessageProducingTask task) {
        return messagesToConsume.size() < maxStoredMessages
                && messagesToConsume.size() <= task.getMaximumAmountOfMessagesToProduce();
    }
}
