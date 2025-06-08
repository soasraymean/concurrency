package p2.exchanger;

import java.util.Queue;
import java.util.concurrent.Exchanger;

public class ConsumingTask extends ExchangingTask {

    public ConsumingTask(Exchanger<Queue<ExchangeableObject>> exchanger) {
        super(exchanger);
    }

    @Override
    protected void handle(Queue<ExchangeableObject> queue) {
        while (!queue.isEmpty()) {
            ExchangeableObject polled = queue.poll();
            System.out.printf("%s was consumed\n", polled);
        }
        System.out.println("---------------------------");
    }
}
