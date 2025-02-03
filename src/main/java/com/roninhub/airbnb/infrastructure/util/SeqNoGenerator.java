package com.roninhub.airbnb.infrastructure.util;

import java.util.concurrent.atomic.AtomicLong;

public class SeqNoGenerator {
    private static final AtomicLong sequence = new AtomicLong(0);


    public static long nextLong() {
        return sequence.incrementAndGet();
    }
}
