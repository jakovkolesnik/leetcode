package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Objects;

public class Synchronized {


    @Test
    public void testLocks() throws InterruptedException {
        LockTest lockTest = new LockTest();
        lockTest.test("first " + LocalTime.now());
        new Thread(() -> lockAndWait(lockTest)).run();
        Thread.sleep(100);
        lockTest.test("second " + LocalTime.now());
    }

    private void lockAndWait(LockTest locker){
        try{
            synchronized(locker) {
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class LockTest {
        public synchronized void test(String text) {
            System.out.println(text);
        }
    }
}
