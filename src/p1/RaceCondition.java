package p1;

import java.util.stream.IntStream;

public class RaceCondition {

    private static int counter = 0;

    private static final int T1_INCREMENT_AMOUNT = 500;
    private static final int T2_INCREMENT_AMOUNT = 600;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = createThread(T1_INCREMENT_AMOUNT);
        Thread t2 = createThread(T2_INCREMENT_AMOUNT);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter);

    }

    private static Thread createThread(int increment) {
        return new Thread(() -> IntStream.range(0, increment).forEach(i -> {

//            synchronized (RaceCondition.class) {
//                counter++;
//            }

            increaseCounter();
        }));
    }

    private static synchronized void increaseCounter() {
        counter++;
    }

}
