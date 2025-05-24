public class ThreadAndRunnableCreation {
    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName());

        Thread thread = new ThreadExtended();
        thread.start();

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        thread2.start();


        Runnable task = () -> System.out.println(Thread.currentThread().getName());
        Thread thread3 = new Thread(task);
        thread3.start();
    }

    private static final class ThreadExtended extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}