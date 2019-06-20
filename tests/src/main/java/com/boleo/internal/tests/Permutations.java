package com.boleo.internal.tests;

import java.util.*;

public class Permutations {

    public List<int[]> allPermutations(int[] input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null");
        }
        return allPermutations(input, 0, input.length - 1);
    }

    public List<int[]> allPermutations(int[] input, int startIndex, int endIndex) {
        if (input == null) {
            throw new IllegalArgumentException("input is null");
        }
        List<int[]> result = new ArrayList<>();
        result.add(input.clone());
        /*
         * Will insert element [i] to all previous permutations at rows startIndex..(i-1)
         * for example if input is {1,2,3}, [i] = 3 and first two permutations are
         * 123
         * 213
         * going to add following permutation
         * 132
         * 231
         * 312
         * 321
         */
        for (int i = startIndex + 1; i <= endIndex; i++) {
            int numberOfPermutations = result.size();
            int insertPosition = i - 1;
            while (insertPosition >= startIndex) {
                for (int j = 0; j < numberOfPermutations; j++) {
                    int[] permutation = result.get(j).clone();
                    int swapValueFromTheRight = permutation[i];
                    permutation[i] = permutation[insertPosition];
                    permutation[insertPosition] = swapValueFromTheRight;
                    result.add(permutation);
                }
                insertPosition--;
            }
        }
        return result;
    }

    public List<int[]> distinctPermutations(int[] input) {
        Map<Integer, Integer> distinct = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            int element = input[i];
            if (distinct.containsKey(element)) {
                distinct.put(element, distinct.get(element) + 1);
            } else {
                distinct.put(element, 1);
            }
        }
        if (distinct.size() == input.length) {
            return allPermutations(input);
        }

        //re-create input array with distinct elements first eg input abacba -> abcaab
        int[] sortedInput = new int[input.length];
        int index = 0;
        for (Integer element : distinct.keySet()) {
            sortedInput[index] = element;
            index++;
        }
        for (Integer element : distinct.keySet()) {
            int count = distinct.get(element);
            for (int i = 1; i < count; i++) {
                sortedInput[index] = element;
                index++;
            }
        }

        return calculateDistinct(sortedInput, distinct.size());
    }

    private List<int[]> calculateDistinct(int[] sortedInput, int distinctSize) {
        Map<Integer, Integer> weights = new HashMap<>();
        for (int i = 0; i < distinctSize; i++) {
            weights.put(sortedInput[i], i);
        }
        long pow = 1;
        long[] powers = new long[sortedInput.length];
        powers[0] = 1;
        for (int i = 1; i < sortedInput.length; i++) {
            pow = pow * distinctSize;
            powers[i] = pow;
        }

        List<int[]> result = allPermutations(sortedInput, 0, distinctSize - 1);
        Set<Long> values = new HashSet<>();
        for (int[] entry : result) {
            values.add(calculateValue(entry, weights, powers));
        }

        for (int i = distinctSize; i < sortedInput.length; i++) {
            int numberOfPermutations = result.size();
            int insertPosition = i - 1;
            while (insertPosition >= 0) {
                for (int j = 0; j < numberOfPermutations; j++) {
                    int[] permutation = result.get(j).clone();
                    int swapValueFromTheRight = permutation[i];
                    int previousValueToSwap = permutation[insertPosition];
                    if (swapValueFromTheRight == previousValueToSwap) {
                        continue;
                    }
                    permutation[i] = previousValueToSwap;
                    permutation[insertPosition] = swapValueFromTheRight;
                    long value = calculateValue(permutation, weights, powers);
                    if (values.add(value)) {
                        result.add(permutation);
                    }
                }
                insertPosition--;
            }
        }
        return result;
    }

    private Long calculateValue(int[] input, Map<Integer, Integer> weights, long[] powers) {
        long result = 0L;
        for (int i = 0; i < input.length; i++) {
            result = result + weights.get(input[i]) * powers[i];
        }
        return result;
    }
}
