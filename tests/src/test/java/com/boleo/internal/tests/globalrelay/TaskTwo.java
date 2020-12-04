package com.boleo.internal.tests.globalrelay;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTwo {

    @Test
    public void test() throws Exception {
        assertEquals(3, figureOutHowManyWaysToCut(new int[]{3, 4, 5, 3, 7}));
    }

    private int figureOutHowManyWaysToCut(final int[] array) {
        if (array == null || array.length < 4) {
            return 0;
        }

        int optionalCut = 0;
        int direction = array[0] - array[1];
        int problems = 0;
        int lastProblemIndex = -1;

        for (int i = 1; i < array.length - 2; i++) {
            int newDirection = array[i] - array[i + 1];
            if ((newDirection >= 0 && direction >= 0) || (direction <= 0 && newDirection <= 0)) {
                problems++;
                if (lastProblemIndex > 0 &&  i - lastProblemIndex > 1){
                    return -1;
                }
                lastProblemIndex = i;
            }
            direction = newDirection;
        }
        if (problems == 0) {
            return 0;
        }
        for (int i = 0; i < array.length; i++) {
            if (aestheticCut(array, i)) {
                optionalCut++;
            }
        }
        return optionalCut;
    }

    private boolean aestheticCut(int[] array, int tree) {
        int leftHeight = 0;
        int centreHeight = 0;
        int rightHeight = 0;
        if (tree > 0 && tree < array.length - 2) {
            leftHeight = array[tree - 1];
            centreHeight = array[tree + 1];
            rightHeight = array[tree + 2];
        } else if (tree == 0) {
            leftHeight = array[1];
            centreHeight = array[2];
            rightHeight = array[3];
        } else if (tree == array.length - 1) {
            leftHeight = array[array.length - 4];
            centreHeight = array[array.length - 3];
            rightHeight = array[array.length - 2];
        } else if (tree == array.length - 2) {
            leftHeight = array[array.length - 4];
            centreHeight = array[array.length - 3];
            rightHeight = array[array.length - 1];
        }

        if (leftHeight > centreHeight && rightHeight > centreHeight) {
            return true;
        }
        if (leftHeight < centreHeight && rightHeight < centreHeight) {
            return true;
        }
        return false;
    }
}
