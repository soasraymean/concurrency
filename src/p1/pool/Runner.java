package p1.pool;

public class Runner {
    public static void main(String[] args) {
        int poolSize = 3;
        ConnectionPool pool = new ConnectionPool(poolSize);
        ConnectionPoolTask task = new ConnectionPoolTask(pool);

        int threadCount = 15;

        Thread[] threads = ThreadUtil.createThreads(task, threadCount);
        ThreadUtil.startThreads(threads);
    }
}
