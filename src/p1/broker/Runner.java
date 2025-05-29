package p1.broker;

import p1.broker.consumer.MessageConsumingTask;
import p1.broker.producer.MessageFactory;
import p1.broker.producer.MessageProducingTask;

import java.util.Arrays;

public class Runner {
    public static void main(String[] args) {

        int brokerMaxStoredMessages = 15;
        MessageBroker messageBroker = new MessageBroker(brokerMaxStoredMessages);
        MessageFactory messageFactory = new MessageFactory();

        Thread producingThread1 = new Thread(
                new MessageProducingTask(messageBroker, messageFactory, brokerMaxStoredMessages, "PRODUCER_1"));
        Thread producingThread2 = new Thread(
                new MessageProducingTask(messageBroker, messageFactory, 10, "PRODUCER_2"));
        Thread producingThread3 = new Thread(
                new MessageProducingTask(messageBroker, messageFactory, 5, "PRODUCER_3"));

        Thread consumingThread1 = new Thread(
                new MessageConsumingTask(messageBroker, 0, "CONSUMER_1"));
        Thread consumingThread2 = new Thread(
                new MessageConsumingTask(messageBroker, 6, "CONSUMER_2"));
        Thread consumingThread3 = new Thread(
                new MessageConsumingTask(messageBroker, 11, "CONSUMER_3"));

        startThreads(
                producingThread1, producingThread2, producingThread3,
                consumingThread1, consumingThread2, consumingThread3);


    }

    private static void startThreads(Thread... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }


}
