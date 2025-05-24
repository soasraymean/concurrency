import java.util.stream.IntStream;

public class SumOf1000 {

    private static final int FIRST_THREAD_FROM = 1;
    private static final int FIRST_THREAD_TO = 500;

    private static final int SECOND_THREAD_FROM = 501;
    private static final int SECOND_THREAD_TO = 1000;


    public static void main(String[] args) throws InterruptedException {

        SumTask task1 = new SumTask(FIRST_THREAD_FROM, FIRST_THREAD_TO);
        SumTask task2 = new SumTask(SECOND_THREAD_FROM, SECOND_THREAD_TO);

        startThreads(task1);
        startThreads(task2);

//        waitForTasksFinish();



        int result = task1.getSum() + task2.getSum();
        printNameAndSum(result);

    }

    private static void waitForTasksFinish() throws InterruptedException {
        Thread.sleep(1_000L);
    }

    private static void printNameAndSum(int number) {
        System.out.println(Thread.currentThread().getName() + ": " + number);
    }

    private static void startThreads(Runnable task) throws InterruptedException {
        Thread thread = new Thread(task);
        thread.start();

        thread.join();
    }

    private static final class SumTask implements Runnable {

        private final int from;
        private final int to;

        private int sum;

        public SumTask(int from, int to) {
            this.from = from;
            this.to = to;
            this.sum = 0;
        }

        @Override
        public void run() {
            this.sum = IntStream.rangeClosed(this.from, this.to).sum();
            printNameAndSum(this.sum);
        }

        public int getSum() {
            return this.sum;
        }
    }
}
