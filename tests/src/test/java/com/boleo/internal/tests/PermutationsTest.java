package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PermutationsTest {

    @Test
    void distinctPermutations() {
        Permutations permutations = new Permutations();
        System.out.println("DISTINCT");
        List<int[]> distinct = permutations.distinctPermutations(new int[]{1, 2, 1});
        for (int[] entry : distinct) {
            System.out.println(Arrays.toString(entry));
        }
    }

    @Test
    void testAll() {
        Permutations permutations = new Permutations();
        System.out.println("ALL");
        List<int[]> result = permutations.allPermutations(new int[]{1, 2, 1});
        for (int[] entry : result) {
            System.out.println(Arrays.toString(entry));
        }
    }
}