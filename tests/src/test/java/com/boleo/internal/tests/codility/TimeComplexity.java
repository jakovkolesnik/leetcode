package com.boleo.internal.tests.codility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeComplexity {
    /*
        A small frog wants to get to the other side of the road. The frog is currently located at position X and wants to get to a position greater than or equal to Y. The small frog always jumps a fixed distance, D.
        Count the minimal number of jumps that the small frog must perform to reach its target.
        Write a function:
            class Solution { public int solution(int X, int Y, int D); }
        that, given three integers X, Y and D, returns the minimal number of jumps from position X to a position equal to or greater than Y.

        For example, given:
          X = 10
          Y = 85
          D = 30

        the function should return 3, because the frog will be positioned as follows:

                after the first jump, at position 10 + 30 = 40
                after the second jump, at position 10 + 30 + 30 = 70
                after the third jump, at position 10 + 30 + 30 + 30 = 100

        Write an efficient algorithm for the following assumptions:

                X, Y and D are integers within the range [1..1,000,000,000];
                X ≤ Y.

     */
    @Test
    public void findNumberOfJumps() throws Exception {
        assertEquals(3, numberOfJumps(10, 85, 30));
    }

    private int numberOfJumps(int start, int finish, int length) {
        int distance = finish - start;
        int whole = distance / length;
        if ((distance % length) > 0) {
            return whole + 1;
        }
        return whole;
    }

    /*
    An array A consisting of N different integers is given. The array contains integers in the range [1..(N + 1)], which means that exactly one element is missing.
    Your goal is to find that missing element.
    Write a function:

        class Solution { public int solution(int[] A); }

    that, given an array A, returns the value of the missing element.

    For example, given array A such that:
      A[0] = 2
      A[1] = 3
      A[2] = 1
      A[3] = 5

    the function should return 4, as it is the missing element.

    Write an efficient algorithm for the following assumptions:

            N is an integer within the range [0..100,000];
            the elements of A are all distinct;
            each element of array A is an integer within the range [1..(N + 1)].

    */
    @Test
    public void findMissingElement() throws Exception {
        int[] array = new int[4];
        array[0] = 2;
        array[1] = 3;
        array[2] = 1;
        array[3] = 5;
        assertEquals(4, findMissing(array));
        array[0] = 4;
        array[1] = 3;
        array[2] = 1;
        array[3] = 2;
        assertEquals(5, findMissing(array));
    }

    private int findMissing(final int[] array) {
        if(array == null || array.length == 0){
            return 1;
        }
        boolean[] flags = new boolean[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            flags[array[i] - 1] = true;
        }
        for (int i = 0; i < flags.length; i++) {
            if (!flags[i]) {
                return i + 1;
            }
        }
        // never happens
        return 1;
    }

    /**
     * A non-empty array A consisting of N integers is given. Array A represents numbers on a tape.
     * <p>
     * Any integer P, such that 0 < P < N, splits this tape into two non-empty parts: A[0], A[1], ..., A[P − 1] and A[P], A[P + 1], ..., A[N − 1].
     * <p>
     * The difference between the two parts is the value of: |(A[0] + A[1] + ... + A[P − 1]) − (A[P] + A[P + 1] + ... + A[N − 1])|
     * <p>
     * In other words, it is the absolute difference between the sum of the first part and the sum of the second part.
     * <p>
     * For example, consider array A such that:
     * A[0] = 3
     * A[1] = 1
     * A[2] = 2
     * A[3] = 4
     * A[4] = 3
     * <p>
     * We can split this tape in four places:
     * <p>
     * P = 1, difference = |3 − 10| = 7
     * P = 2, difference = |4 − 9| = 5
     * P = 3, difference = |6 − 7| = 1
     * P = 4, difference = |10 − 3| = 7
     * <p>
     * Write a function:
     * <p>
     * class Solution { public int solution(int[] A); }
     * <p>
     * that, given a non-empty array A of N integers, returns the minimal difference that can be achieved.
     * <p>
     * For example, given:
     * A[0] = 3
     * A[1] = 1
     * A[2] = 2
     * A[3] = 4
     * A[4] = 3
     * <p>
     * the function should return 1, as explained above.
     * <p>
     * Write an efficient algorithm for the following assumptions:
     * <p>
     * N is an integer within the range [2..100,000];
     * each element of array A is an integer within the range [−1,000..1,000].
     */

    @Test
    public void tapeEquilibrium() throws Exception {
        int[] array = new int[5];
        array[0] = 3;
        array[1] = 1;
        array[2] = 2;
        array[3] = 4;
        array[4] = 3;
        assertEquals(1, findMinimalDiff(array));
    }

    private int findMinimalDiff(int[] array) {
        int sumRight = 0;
        for (int i = 1; i < array.length; i++) {
            sumRight = sumRight + array[i];
        }
        int sumLeft = array[0];
        int minimum = Math.abs(sumLeft - sumRight);
        for (int i = 1; i < array.length - 1; i++) {
            sumLeft = sumLeft + array[i];
            sumRight = sumRight - array[i];
            int newSum = Math.abs(sumLeft - sumRight);
            if (newSum < minimum) {
                minimum = newSum;
            }
        }
        return minimum;
    }
}
