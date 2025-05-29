package p1;

import java.util.concurrent.TimeUnit;

public class Interruption {
    public static void main(String[] args) throws InterruptedException {

        Thread serverThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    doRequest();
                }
            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().isInterrupted());

                System.out.println("Server interrupted");
            }
        });
        serverThread.setName("Server Thread");
        serverThread.start();

        Thread stopThread = new Thread(() -> {
            if (isServerShouldBeOff()) {
                serverThread.interrupt();
                stopServer();
            }
        });
        TimeUnit.SECONDS.sleep(5L);
        stopThread.start();

    }

    private static void stopServer() {
        System.out.println("Server stopped");
    }

    private static boolean isServerShouldBeOff() {
        return true;
    }

    private static void doRequest() throws InterruptedException {
        System.out.println("Requesting data...");
        TimeUnit.SECONDS.sleep(1L);
        System.out.println("Requesting data complete.");
    }
}
