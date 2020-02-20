package com.boleo.internal.tests.ngram;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void splitByNewLine() {
        String input = "This was my father's belief\n" +
                "  And this is also mine:\n" +
                "Let the corn be all one sheaf--\n" +
                "\r\n"+
                "\r\n"+
                "  And the grapes be all one vine,\n" +
                "\r"+
                "\r"+
                "Ere our children's teeth are set on edge\n" +
                "\n"+
                "  By bitter bread and wine.";

        String[] result = Utils.splitByNewLine(input);
        assertEquals(6, result.length);
        assertEquals("This was my father's belief", result[0]);
        assertEquals("  And this is also mine:", result[1]);
        assertEquals("Let the corn be all one sheaf--", result[2]);
        assertEquals("  And the grapes be all one vine,", result[3]);
        assertEquals("Ere our children's teeth are set on edge", result[4]);
        assertEquals("  By bitter bread and wine.", result[5]);
    }

    @Test
    void splitIntoWords() {
        String input = "Let!! the,, =corn+- %be% all one sheaf--";
        String[] result = Utils.splitIntoWords(input);
        assertEquals(7, result.length);
        assertEquals("Let", result[0]);
        assertEquals("the", result[1]);
        assertEquals("corn", result[2]);
        assertEquals("be", result[3]);
        assertEquals("all", result[4]);
        assertEquals("one", result[5]);
        assertEquals("sheaf", result[6]);
        Double d = 0.1;
        System.out.println(String.format("%.3f", d));
        System.out.println(String.format("%.3f", 1d));
        System.out.println(String.format("%.3f", 2.003d));
        System.out.println(String.format("%.3f", 4.0003d));
    }



}