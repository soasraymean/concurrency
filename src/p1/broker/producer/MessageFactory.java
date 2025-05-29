package p1.broker.producer;

import p1.broker.model.Message;

public class MessageFactory {

    private static final int INITIAL_MESSAGE_INDEX = 1;
    private static final String MESSAGE_DATA_TEMPLATE = "Message%d";

    private int nextMessageIndex;

    public MessageFactory() {
        nextMessageIndex = INITIAL_MESSAGE_INDEX;
    }

    public Message createMessage() {
        return new Message(String.format(MESSAGE_DATA_TEMPLATE, findAndIncrementNextMessageIndex()));
    }

    private synchronized int findAndIncrementNextMessageIndex() {
        return nextMessageIndex++;
    }
}
