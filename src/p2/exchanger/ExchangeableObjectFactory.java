package p2.exchanger;

import java.util.concurrent.TimeUnit;

public class ExchangeableObjectFactory {

    private long nextId;

    public ExchangeableObject createExchangeableObject() {
        try {
            TimeUnit.SECONDS.sleep(2);
            return new ExchangeableObject(nextId++);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
