package p2.exchanger;

import java.util.Queue;
import java.util.concurrent.Exchanger;
import java.util.stream.IntStream;

public class ProducingTask extends ExchangingTask {

    private ExchangeableObjectFactory factory;
    private int nObjectsToProduce;

    public ProducingTask(Exchanger<Queue<ExchangeableObject>> exchanger, ExchangeableObjectFactory factory, int nObjectsToProduce) {
        super(exchanger);
        this.factory = factory;
        this.nObjectsToProduce = nObjectsToProduce;
    }

    @Override
    protected void handle(Queue<ExchangeableObject> queue) {
        IntStream.range(0, nObjectsToProduce)
                .mapToObj(i -> factory.createExchangeableObject())
                .peek(exchangeableObject -> System.out.printf("%s was produced\n", exchangeableObject))
                .forEach(queue::add);
    }
}
