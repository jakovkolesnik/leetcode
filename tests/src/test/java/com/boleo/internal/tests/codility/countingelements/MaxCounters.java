package com.boleo.internal.tests.codility.countingelements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxCounters {
    /**
     * You are given N counters, initially set to 0, and you have two possible operations on them:
     * <p>
     * increase(X) − counter X is increased by 1,
     * max counter − all counters are set to the maximum value of any counter.
     * <p>
     * A non-empty array A of M integers is given. This array represents consecutive operations:
     * <p>
     * if A[K] = X, such that 1 ≤ X ≤ N, then operation K is increase(X),
     * if A[K] = N + 1 then operation K is max counter.
     * <p>
     * For example, given integer N = 5 and array A such that:
     * A[0] = 3
     * A[1] = 4
     * A[2] = 4
     * A[3] = 6
     * A[4] = 1
     * A[5] = 4
     * A[6] = 4
     * <p>
     * the values of the counters after each consecutive operation will be:
     * (0, 0, 1, 0, 0)
     * (0, 0, 1, 1, 0)
     * (0, 0, 1, 2, 0)
     * (2, 2, 2, 2, 2)
     * (3, 2, 2, 2, 2)
     * (3, 2, 2, 3, 2)
     * (3, 2, 2, 4, 2)
     * <p>
     * The goal is to calculate the value of every counter after all operations.
     * <p>
     * Write a function:
     * <p>
     * class Solution { public int[] solution(int N, int[] A); }
     * <p>
     * that, given an integer N and a non-empty array A consisting of M integers, returns a sequence of integers representing the values of the counters.
     * <p>
     * Result array should be returned as an array of integers.
     * <p>
     * For example, given:
     * A[0] = 3
     * A[1] = 4
     * A[2] = 4
     * A[3] = 6
     * A[4] = 1
     * A[5] = 4
     * A[6] = 4
     * <p>
     * the function should return [3, 2, 2, 4, 2], as explained above.
     * <p>
     * Write an efficient algorithm for the following assumptions:
     * <p>
     * N and M are integers within the range [1..100,000];
     * each element of array A is an integer within the range [1..N + 1].
     */

    @Test
    public void test() throws Exception {
        int[] array = new int[7];
        array[0] = 3;
        array[1] = 4;
        array[2] = 4;
        array[3] = 6;
        array[4] = 1;
        array[5] = 4;
        array[6] = 4;
        assertArrayEquals(new int[]{3,2,2,4,2}, count(array, 5));
    }

    private int[] count(final int[] operations, final int numberOfCounters) {
        if (numberOfCounters < 0) {
            return null;
        }
        int[] result = new int[numberOfCounters];
        if (operations == null || operations.length == 0) {
            return result;
        }
        int currentMax = 0;
        int potentialNewMax = 0;

        for (int i = 0; i < operations.length; i++) {
            int operation = operations[i];

            if (operation > numberOfCounters) {
                currentMax = potentialNewMax;
                continue;
            }

            int oldValue = result[operation - 1];
            if (oldValue < currentMax){
                oldValue = currentMax;
            }
            int newValue = oldValue + 1;
            result[operation - 1] = newValue;
            if (potentialNewMax < newValue) {
                potentialNewMax = newValue;
            }
        }

        for (int i = 0; i < result.length; i++) {
            int value = result[i];
            if (value < currentMax) {
                result[i] = currentMax;
            }
        }
        return result;
    }
}
