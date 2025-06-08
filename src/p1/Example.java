package p1;

import java.util.stream.IntStream;

public class Example {

    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(new Task(100));
        Thread t2 = new Thread(new Task(100));
        Thread t3 = new Thread(new Task(100));


        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(counter);

    }

    private static class Task implements Runnable {

        private int n;

        public Task(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            IntStream.range(0, n).forEach(i -> {
                synchronized (Example.class) {
                    counter++;
                }
            });
        }
    }

    private synchronized static void increaseCounter() {
        counter++;
    }

}
