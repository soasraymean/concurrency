package p1;

import java.util.concurrent.TimeUnit;

public class VolatileExa {
    public static void main(String[] args) throws InterruptedException {

        PrintingTask task = new PrintingTask();
        Thread thread = new Thread(task);
        thread.start();

        TimeUnit.SECONDS.sleep(5);

        task.setShouldPrint(false);
        System.out.println("Task should be stopped");

    }

    private static class PrintingTask implements Runnable {
        private volatile boolean shouldPrint = true;

        public boolean isShouldPrint() {
            return shouldPrint;
        }

        public void setShouldPrint(boolean shouldPrint) {
            this.shouldPrint = shouldPrint;
        }

        @Override
        public void run() {
            try {
                while (shouldPrint) {
                    System.out.println("In progress...");
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}