package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class Threading {


    @Test
    public void test() throws Exception {
        final long[] array = new long[10];
        final AtomicBoolean cancel = new AtomicBoolean(false);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<Future<?>> tasks = new ArrayList<>(5);
        tasks.add(pool.submit(() -> {
            while (!cancel.get()) {
                array[0] = 0L;
            }
        }));
        tasks.add(pool.submit(() -> {
            while (!cancel.get()) {
                array[0] = Long.MAX_VALUE;
            }
        }));
        tasks.add(pool.submit(() -> {
            while (!cancel.get()) {
                array[0] = Long.MIN_VALUE;
            }
        }));
        tasks.add(pool.submit(() -> {
            while (!cancel.get()) {
                array[0] = 100500L;
            }
        }));
        tasks.add(pool.submit(() -> {
            while (!cancel.get()) {
                long value = array[0];
                if (value != 0L && value != Long.MAX_VALUE && value != Long.MIN_VALUE && value !=100500L) {
                    System.out.println(value);
                }
            }
        }));

        Thread.sleep(600000);
        cancel.set(true);
        for (Future<?> task : tasks) {
            task.get();
        }
    }



}
