package p2.exchanger;

import java.util.Queue;
import java.util.concurrent.Exchanger;

public class Runner {
    public static void main(String[] args) {

        Exchanger<Queue<ExchangeableObject>> exchanger = new Exchanger<>();

        ExchangeableObjectFactory factory = new ExchangeableObjectFactory();

        int nItemsToProduce = 3;

        ProducingTask producingTask = new ProducingTask(exchanger, factory, nItemsToProduce);
        ConsumingTask consumingTask = new ConsumingTask(exchanger);

        new Thread(producingTask).start();
        new Thread(consumingTask).start();


    }
}
