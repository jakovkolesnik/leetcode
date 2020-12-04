package com.boleo.internal.tests.codility.countingelements;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermCheck {
    /**
     A non-empty array A consisting of N integers is given.

     A permutation is a sequence containing each element from 1 to N once, and only once.

     For example, array A such that:
     A[0] = 4
     A[1] = 1
     A[2] = 3
     A[3] = 2

     is a permutation, but array A such that:
     A[0] = 4
     A[1] = 1
     A[2] = 3

     is not a permutation, because value 2 is missing.

     The goal is to check whether array A is a permutation.

     Write a function:

     class Solution { public int solution(int[] A); }

     that, given an array A, returns 1 if array A is a permutation and 0 if it is not.

     For example, given array A such that:
     A[0] = 4
     A[1] = 1
     A[2] = 3
     A[3] = 2

     the function should return 1.

     Given array A such that:
     A[0] = 4
     A[1] = 1
     A[2] = 3

     the function should return 0.

     Write an efficient algorithm for the following assumptions:

     N is an integer within the range [1..100,000];
     each element of array A is an integer within the range [1..1,000,000,000].
     */

    @Test
    public void test() throws Exception {
        assertEquals(1, isPermutation(new int[]{4,1,3,2}));
        assertEquals(0, isPermutation(new int[]{4,1,2}));
    }

    private int isPermutation(final int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        Set<Integer> counted = new HashSet<>();

        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            if (value > array.length){
                return 0;
            }
            if (!counted.add(value)) {
                return 0;
            }
        }
        return 1;
    }
}
