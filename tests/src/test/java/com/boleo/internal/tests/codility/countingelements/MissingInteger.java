package com.boleo.internal.tests.codility.countingelements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MissingInteger {
    /**
     This is a demo task.

     Write a function:

     class Solution { public int solution(int[] A); }

     that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.

     For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.

     Given A = [1, 2, 3], the function should return 4.

     Given A = [−1, −3], the function should return 1.

     Write an efficient algorithm for the following assumptions:

     N is an integer within the range [1..100,000];
     each element of array A is an integer within the range [−1,000,000..1,000,000].
     */

    @Test
    public void test() throws Exception {
        int[] array = new int[3];
        array[0] = 1;
        array[1] = 2;
        array[2] = 3;
        assertEquals(4, findMissing(array));
        assertEquals(5, findMissing(new int[]{1, 3, 6, 4, 1, 2}));
        assertEquals(1, findMissing(new int[]{8, 9, 10}));
    }

    private int findMissing(final int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }
        boolean[] flags = new boolean[array.length];

        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            if (value > 0 && value < (array.length + 1)) {
                flags[value - 1] = true;
            }
        }

        for (int i = 0; i < flags.length; i++) {
            if(flags[i] == false){
                return i+1;
            }
        }
        return flags.length + 1;
    }
}
