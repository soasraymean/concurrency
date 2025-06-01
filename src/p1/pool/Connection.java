package p1.pool;

public class Connection {
    private long id;
    private boolean autoCommit;

    public Connection(long id, boolean autoCommit) {
        this.id = id;
        this.autoCommit = autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public long getId() {
        return id;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Connection that = (Connection) o;
        return id == that.id && autoCommit == that.autoCommit;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + Boolean.hashCode(autoCommit);
        return result;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id=" + id +
                ", autoCommit=" + autoCommit +
                '}';
    }
}
