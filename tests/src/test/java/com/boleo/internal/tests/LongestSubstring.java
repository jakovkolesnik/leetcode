package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestSubstring {
    /**
     * https://leetcode.com/problems/longest-substring-without-repeating-characters/
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     *
     * @param s
     * @return
     */
    @Test
    void testLongestSubstring() {
        assertEquals(3, lengthOfLongestSubstring("abcabcbb"));
        assertEquals(1, lengthOfLongestSubstring("bbbbb"));
        assertEquals(3, lengthOfLongestSubstring("pwwkew"));
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        Map<Character, Integer> positions = new HashMap<>();
        int result = 0;
        int startOfCurrentSubstring = 0;
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (positions.containsKey(c)) {
                startOfCurrentSubstring = Math.max(startOfCurrentSubstring, positions.get(c) + 1);
            }
            positions.put(c, i);
            if ((i - startOfCurrentSubstring + 1) > result) {
                result = i - startOfCurrentSubstring + 1;
            }
        }
        return result;
    }
}
