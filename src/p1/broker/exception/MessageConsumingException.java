package p1.broker.exception;

public class MessageConsumingException extends RuntimeException {

    public MessageConsumingException() {}

    public MessageConsumingException(String message) {
        super(message);
    }

    public MessageConsumingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageConsumingException(Throwable cause) {
        super(cause);
    }
}
