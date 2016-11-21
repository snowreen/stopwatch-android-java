package edu.luc.etl.cs313.android.simplestopwatch.model.counter;

import java8.util.function.BooleanSupplier;

/**
 * Created by snowreen on 11/19/16.
 */

public class DefaultCounterModel implements CounterModel {
    /** The lower bound of the counter. */
    private final int min;

    /** The upper bound of the counter. */
    private final int max;

    /** The current value of the counter. */
    private int value;

    /** Constructs a bounded counter with the default bounds. */
    public DefaultCounterModel() {
        this(0, 10);
    }

    /**
     * Constructs a bounded counter with the given bounds.
     *
     * @param min the lower bound
     * @param max the upper bound
     * @throws IllegalArgumentException if min > max
     */
    public DefaultCounterModel(final int min, final int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min >= max");
        }
        this.min = min;
        this.max = max;
        this.value = this.min;
    }

    /**
     * Indicates whether this counter currently satisfies its internal data
     * invariant: the counter value is within the bounds.
     *
     * @return whether the data invariant is satisfied
     */
    protected boolean dataInvariant() {
        return min <= value && value <= max;
    }

    @Override
    public void increment() {
        if (dataInvariant() && !isFull()) {
            ++value;
        }
    }

    @Override
    public void decrement() {
        if (dataInvariant() && !isEmpty()) {
            --value;
        }
    }

    @Override
    public void reset() {
        while (!isEmpty()) {
            decrement();
        }
    }

    @Override
    public int get() {
        return value;
    }

    @Override
    public boolean isFull() {
        return value >= max;
    }

    @Override
    public boolean isEmpty() {
        return value <= min;
    }
}
