package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedianTwoSortedArrays {

    /**
     * https://leetcode.com/problems/median-of-two-sorted-arrays/
     * There are two sorted arrays nums1 and nums2 of size m and n respectively.
     * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
     * You may assume nums1 and nums2 cannot be both empty.
     */
    @Test
    void testMedianSortedArrays() {
        assertEquals(2d, findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
        assertEquals(2d, findMedianSortedArrays(new int[]{2}, new int[]{1, 3}));
        assertEquals(2.5, findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
        assertEquals(2.5, findMedianSortedArrays(new int[]{1, 4}, new int[]{2, 3}));
        assertEquals(5.5, findMedianSortedArrays(new int[]{1, 2, 3, 4, 10}, new int[]{5, 6, 7, 8, 9}));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int nextIndexOfFirstArray = 0;
        int nextIndexOfSecondArray = 0;
        int median = (nums1.length + nums2.length) / 2;

        int value = 0;
        int previousValue = 0;

        int i = 0;
        while (i <= median) {
            previousValue = value;
            if (nums2.length == nextIndexOfSecondArray
                    ||
                    (
                            (nums1.length > nextIndexOfFirstArray)
                                    &&
                                    (nums1[nextIndexOfFirstArray] <= nums2[nextIndexOfSecondArray]))) {
                value = nums1[nextIndexOfFirstArray];
                nextIndexOfFirstArray++;
            } else {
                value = nums2[nextIndexOfSecondArray];
                nextIndexOfSecondArray++;
            }
            i++;
        }
        if ((nums1.length + nums2.length) % 2 == 0) {
            return (value + previousValue) / 2d;
        }
        return value;
    }

    public double findMedianSortedArraysBinarySearch(int[] nums1, int[] nums2) {

        if (nums2.length < nums1.length) {
            int[] tmp = nums1;
            nums1 = nums2;
            nums2 = tmp;
        }

        int value = 0;
        int previousValue = 0;

        int i = nums1.length / 2;
        while (i >= 0 && i < nums1.length) {

        }
        return 1d;
    }
}