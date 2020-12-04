package com.boleo.internal.tests.codility.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntersectingDiscs {
    /**
     * We draw N discs on a plane. The discs are numbered from 0 to N − 1. An array A of N non-negative integers, specifying the radiuses of the discs, is given. The J-th disc is drawn with its center at (J, 0) and radius A[J].
     * <p>
     * We say that the J-th disc and K-th disc intersect if J ≠ K and the J-th and K-th discs have at least one common point (assuming that the discs contain their borders).
     * <p>
     * The figure below shows discs drawn for N = 6 and A as follows:
     * A[0] = 1
     * A[1] = 5
     * A[2] = 2
     * A[3] = 1
     * A[4] = 4
     * A[5] = 0
     * <p>
     * There are eleven (unordered) pairs of discs that intersect, namely:
     * <p>
     * discs 1 and 4 intersect, and both intersect with all the other discs;
     * disc 2 also intersects with discs 0 and 3.
     * <p>
     * Write a function:
     * <p>
     * class Solution { public int solution(int[] A); }
     * <p>
     * that, given an array A describing N discs as explained above, returns the number of (unordered) pairs of intersecting discs. The function should return −1 if the number of intersecting pairs exceeds 10,000,000.
     * <p>
     * Given array A shown above, the function should return 11, as explained above.
     * <p>
     * Write an efficient algorithm for the following assumptions:
     * <p>
     * N is an integer within the range [0..100,000];
     * each element of array A is an integer within the range [0..2,147,483,647].
     */

    @Test
    public void test() throws Exception {
        int[] array = new int[6];
        array[0] = 1;
        array[1] = 5;
        array[2] = 2;
        array[3] = 1;
        array[4] = 4;
        array[5] = 0;
        assertEquals(11, numberOfIntersectsSorting(array));
    }

    private int numberOfIntersects(final int[] array) {
        int sum = 0;
        int[] starts = new int[array.length];
        int[] ends = new int[array.length];
        for (int centre = 0; centre < array.length; centre++) {
            int radius = array[centre];
            if (centre < radius) {
                starts[0] = starts[0] + 1;
            } else {
                starts[centre - radius] = starts[centre - radius] + 1;
            }
            if ((centre + radius) >= array.length) {
                ends[array.length - 1] = ends[array.length - 1] + 1;
            } else {
                ends[centre + radius] = ends[centre + radius] + 1;
            }
        }
        int active = 0;
        for (int i = 0; i < array.length; i++) {
            sum = sum + active * starts[i] + (starts[i] * (starts[i] - 1)) / 2;
            if (sum > 10000000) return -1;
            active += starts[i] - ends[i];
        }
        return sum;
    }

    private int numberOfIntersectsSorting(final int[] array) {
        int sum = 0;
        int[] starts = new int[array.length];
        int[] ends = new int[array.length];
        for (int centre = 0; centre < array.length; centre++) {
            starts[centre] = centre - array[centre];
            ends[centre] = centre + array[centre];
        }

        java.util.Arrays.sort(ends);
        java.util.Arrays.sort(starts);
        int i = 0;
        int j = 0;
        while (i < array.length && j < array.length) {
            if (starts[i] < ends[j]) {
                sum++;
                i++;
            } else {
                sum--;
                j++;
            }
        }
        return sum;
    }
}
