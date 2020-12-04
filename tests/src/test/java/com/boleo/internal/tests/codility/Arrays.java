package com.boleo.internal.tests.codility;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Arrays {

    @Test
    public void arrayShift() throws Exception {
        int[] array = new int[6];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        System.out.println(java.util.Arrays.toString(array));
        System.out.println(java.util.Arrays.toString(shiftRight(array, 23)));
        shiftRightInline(array, 23);
        System.out.println(java.util.Arrays.toString(array));
        int[] empty = new int[0];
        System.out.println(java.util.Arrays.toString(shiftRight(empty, 2)));
    }

    private int[] shiftRight(final int[] array, final int steps) {
        if (array == null) {
            return null;
        }
        int[] result = array.clone();
        if (steps <= 0 || array.length < 2) {
            return result;
        }
        int actualSteps = steps % array.length;
        if (actualSteps == 0) {
            return result;
        }

        for (int i = 0; i < array.length; i++) {
            int newIndex = (i + actualSteps) % result.length;
            result[newIndex] = array[i];
        }

        return result;
    }

    private void shiftRightInline(final int[] array, final int steps) {
        int actualSteps = steps % array.length;
        if (actualSteps == 0) {
            return;
        }
        reverse(array, 0, array.length - 1);
        reverse(array, 0, actualSteps - 1);
        reverse(array, actualSteps, array.length - 1);
    }

    private void reverse(int[] array, int from, int to) {
        int median = (to - from) / 2;
        for (int i = 0; i <= median; i++) {
            int tmp = array[from + i];
            array[from + i] = array[to - i];
            array[to - i] = tmp;
        }
    }

    /**
     * A non-empty array A consisting of N integers is given. The array contains an odd number of elements, and each element of the array can be paired with another element that has the same value, except for one element that is left unpaired.
     * <p>
     * For example, in array A such that:
     * A[0] = 9  A[1] = 3  A[2] = 9
     * A[3] = 3  A[4] = 9  A[5] = 7
     * A[6] = 9
     * <p>
     * the elements at indexes 0 and 2 have value 9,
     * the elements at indexes 1 and 3 have value 3,
     * the elements at indexes 4 and 6 have value 9,
     * the element at index 5 has value 7 and is unpaired.
     * <p>
     * Write a function:
     * <p>
     * class Solution { public int solution(int[] A); }
     * <p>
     * that, given an array A consisting of N integers fulfilling the above conditions, returns the value of the unpaired element.
     * <p>
     * For example, given array A such that:
     * A[0] = 9  A[1] = 3  A[2] = 9
     * A[3] = 3  A[4] = 9  A[5] = 7
     * A[6] = 9
     * <p>
     * the function should return 7, as explained in the example above.
     * <p>
     * Write an efficient algorithm for the following assumptions:
     * <p>
     * N is an odd integer within the range [1..1,000,000];
     * each element of array A is an integer within the range [1..1,000,000,000];
     * all but one of the values in A occur an even number of times.
     */

    @Test
    public void findOddElement() throws Exception {
        int[] array = new int[7];
        array[0] = 9;
        array[1] = 3;
        array[2] = 9;
        array[3] = 3;
        array[4] = 9;
        array[5] = 7;
        array[6] = 9;
        assertEquals(7, findOdd(array));
    }

    private int findOdd(final int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] result = array.clone();
        if (array.length == 1) {
            return array[0];
        }
        Set<Integer> matches = new HashSet<>();

        for (int i = 0; i < array.length; i++) {
            if (!matches.remove(array[i])) {
                matches.add(array[i]);
            }
        }

        return matches.iterator().next();
    }
}
