package p1.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class EvenNumberGenerator {

    public static final int GENERATION_DELTA = 2;

    private final AtomicInteger value = new AtomicInteger();

    public int generate() {
        return value.getAndAdd(GENERATION_DELTA);
    }

    public int getValue() {
        return value.intValue();
    }
}
