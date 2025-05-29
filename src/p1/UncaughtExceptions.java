package p1;

public class UncaughtExceptions {
    public static void main(String[] args) {

        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (thread, exception) -> {
            System.out.println(exception.getMessage() + " " + thread.getName());
        };

//        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);

        Thread thread = new Thread(new Task());
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        thread.start();

    }



    private static class Task implements Runnable {

        @Override
        public void run() {
            throw new RuntimeException("Task uncaught exception");
        }
    }
}
