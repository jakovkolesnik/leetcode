package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class Permutation {

    @Test
    public void testDistinct() {
        System.out.println("DISTINCT");
        Collection<List<Integer>> distinct = distinctPermutation(new int[]{1, 1, 2, 2});
        for (List<Integer> list : distinct) {
            System.out.println(list);
        }
    }

    @Test
    public void testAll() {
        System.out.println("ALL");
        Collection<List<Integer>> all = allPermutation(new int[]{1, 1, 2, 2});
        for (List<Integer> list : all) {
            System.out.println(list);
        }
    }

    public Collection<List<Integer>> distinctPermutation(int[] input) {
        Map<Long, List<Integer>> result = new HashMap<>();
        Map<Integer, Integer> map = new HashMap<>();
        Integer counter = 0;
        for (int i = 0; i < input.length; i++) {
            if (!map.containsKey(input[i])) {
                map.put(input[i], counter);
                counter++;
            }
        }

        allPermutation(input).forEach(p -> result.put(calculateValue(map, p), p));
        return result.values();
    }

    private Long calculateValue(Map<Integer, Integer> map, List<Integer> permutation) {
        long result = 0L;
        int base = map.size();
        long pow = 1;
        for (int i = 0; i < permutation.size(); i++) {
            result = result + permutation.get(i).longValue() * pow;
            pow = pow * base;
        }
        return result;
    }

    public List<List<Integer>> allPermutation(int[] input) {
        if (input == null) {
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        result.add(Arrays.stream(input).boxed().collect(Collectors.toList()));
        if (input.length < 2) {
            return result;
        }

        for (int i = 1; i < input.length; i++) {
            int numberOfPermutations = result.size();
            int insertPosition = i - 1;
            /*
             * Will insert element [i] to all previous permutations at rows 0..(i-1)
             * for example if input is {1,2,3}, [i] = 3 and first two permutations are
             * 123 and 213
             * going to add following permutation
             * 132
             * 231
             * 312
             * 321
             */
            while (insertPosition >= 0) {
                for (int j = 0; j < numberOfPermutations; j++) {
                    List<Integer> permutation = new ArrayList<>(result.get(j));
                    int swapValueFromTheRight = permutation.get(i);
                    int previousValueToSwap = permutation.get(insertPosition);
                    permutation.set(i, previousValueToSwap);
                    permutation.set(insertPosition, swapValueFromTheRight);
                    result.add(permutation);
                }
                insertPosition--;
            }
        }
        return result;
    }
}
