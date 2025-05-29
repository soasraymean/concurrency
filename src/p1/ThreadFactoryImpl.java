package p1;

import java.util.concurrent.ThreadFactory;

public class ThreadFactoryImpl {
    public static void main(String[] args) throws InterruptedException {

        Thread.UncaughtExceptionHandler handler = ((t, e) -> {
            System.out.println(e.getMessage() + " " + t.getName());
        });

        ThreadFactory factory = new DaemonThreadFactory(handler);

        Thread t1 = factory.newThread(new Task());
        t1.start();
        Thread t2 = factory.newThread(new Task());
        t2.start();

        t1.join();
        t2.join();

    }

    private static class DaemonThreadFactory implements ThreadFactory {

        private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

        private DaemonThreadFactory(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            t.setDaemon(true);
            return t;
        }
    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().isDaemon());
            throw new RuntimeException("Runtime uncaught exception");
        }
    }
}
