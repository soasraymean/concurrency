package p2.exchanger;

public class ExchangeableObject {

    private long id;

    public ExchangeableObject(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ExchangeableObject{" +
                "id=" + id +
                '}';
    }
}
