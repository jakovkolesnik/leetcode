package com.boleo.internal.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZigZagString {

    @Test
    /**
     * https://leetcode.com/problems/zigzag-conversion/
     * Input: s = "PAYPALISHIRING", numRows = 4
     * Output: "PINALSIGYAHRPI"
     * Explanation:
     *
     * P     I    N
     * A   L S  I G
     * Y A   H R
     * P     I
     */
    public void testZigZag() {
        assertEquals("1726354", readZigZag("1234567", 4));
        assertEquals("PINALSIGYAHRPI", readZigZag("PAYPALISHIRING", 4));
        assertEquals("PYAIHRNAPLSIIG", readZigZag("PAYPALISHIRING", 2));
    }

    private String readZigZag(String s, int rowCount) {
        if (rowCount < 2){
            return s;
        }
        char[] result = new char[s.length()];
        int resultIndex = 0;
        for (int row = 0; row < rowCount; row++) {
            int inputIndex = row;
            int d = rowCount - 1 - row;
            while (inputIndex < s.length()) {
                result[resultIndex] = s.charAt(inputIndex);
                resultIndex++;
                if ((d > 0 && (d + 1) < rowCount) && (inputIndex + 2*d) < s.length()) {
                    result[resultIndex] = s.charAt(inputIndex + 2*d);
                    resultIndex++;
                }
                inputIndex = inputIndex + (rowCount) + (rowCount - 2);
            }
        }
        return new String(result);
    }
}
