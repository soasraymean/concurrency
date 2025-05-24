public class ThreadStates {
    public static void main(String[] args) throws InterruptedException {

        Thread mainThread = Thread.currentThread();

        Thread thread = new Thread(() -> {
            try {
//                mainThread.join(); WAITING
                mainThread.join(3000); // TIMED-WAITING
                System.out.println(Thread.currentThread().getState() + " " + Thread.currentThread().getName());
//                throw new RuntimeException();
                } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println(thread.getState() + " " + thread.getName());
        thread.join();
        System.out.println(thread.getState() + " " + thread.getName());
    }
}
