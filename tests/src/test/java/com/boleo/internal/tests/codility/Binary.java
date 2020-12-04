package com.boleo.internal.tests.codility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Binary {

    @Test
    public void test() throws Exception {
        assertEquals(2, longestConsecZeroes(9));
        assertEquals(0, longestConsecZeroes(0));
        assertEquals(1, longestConsecZeroes(-10));
        assertEquals(3, longestConsecZeroes(561892));
    }

    private int longestConsecZeroes(final int N) {
        String binary = Integer.toBinaryString(N);
        int max = 0;
        int score = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                if (score > max) {
                    max = score;
                }
                score = 0;
            } else {
                score++;
            }
        }
        return max;
    }
}
