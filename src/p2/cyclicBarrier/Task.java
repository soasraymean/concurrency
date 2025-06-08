package p2.cyclicBarrier;

public abstract class Task {
    private long id;

    public Task(long id) {
        this.id = id;
    }

    public abstract void perform();


    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "id=" + id +
                '}';
    }
}
