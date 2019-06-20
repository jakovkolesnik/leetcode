package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestPalindrome {
    /**
     * https://leetcode.com/problems/longest-palindromic-substring/
     * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
     */
    @Test
    void testLongestPalindrome() {
        assertEquals("bab", longestPalindrome("babad"));
        assertEquals("bb", longestPalindrome("cbbd"));
    }

    public static String longestPalindrome(String s) {

        int maxLeftBoundary = 0;
        int maxRightBoundary = 0;

        int leftBoundary = 0;
        int rightBoundary = 0;

        boolean isPalindrome = false;
        if (s.isEmpty() || s.length() == 1) {
            return s;
        }

        for (int i = 1; i < s.length(); i++) {
            isPalindrome = false;
            if (s.charAt(i) == (s.charAt(i - 1))) {
                isPalindrome = true;
                leftBoundary = i - 1;
                rightBoundary = i;
            } else if (i + 1 < s.length() && s.charAt(i + 1) == (s.charAt(i - 1))) {
                isPalindrome = true;
                leftBoundary = i - 1;
                rightBoundary = i + 1;
            }

            while (isPalindrome && leftBoundary >= 0 && rightBoundary < s.length()) {
                if ((maxRightBoundary - maxLeftBoundary) < (rightBoundary - leftBoundary)) {
                    maxRightBoundary = rightBoundary;
                    maxLeftBoundary = leftBoundary;
                }
                leftBoundary--;
                rightBoundary++;
                if (leftBoundary >= 0 && rightBoundary < s.length()) {
                    isPalindrome = s.charAt(leftBoundary) == (s.charAt(rightBoundary));
                }
            }
        }

        return maxRightBoundary == s.length() - 1 ? s.substring(maxLeftBoundary)
                : s.substring(maxLeftBoundary, maxRightBoundary + 1);
    }
}
