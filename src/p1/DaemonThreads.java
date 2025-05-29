package p1;

import java.util.concurrent.TimeUnit;

public class DaemonThreads {
    public static void main(String[] args) throws InterruptedException {

//        System.out.println(Thread.currentThread().isDaemon());

        //not a daemon
//        Thread thread = new Thread(new Task());
//        thread.setDaemon(true);
//        thread.start();
//        System.out.println(thread.isDaemon());


        Thread parentDaemon = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().isDaemon());
                Thread childDaemon = new Thread(() -> {
                    System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().isDaemon());
                });
                childDaemon.start();
                childDaemon.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        parentDaemon.setDaemon(true);
        parentDaemon.start();

        parentDaemon.join();

        System.out.println("Main thread finished");

    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("Working...");
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
