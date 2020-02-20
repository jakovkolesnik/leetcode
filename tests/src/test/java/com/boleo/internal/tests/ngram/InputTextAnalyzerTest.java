package com.boleo.internal.tests.ngram;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InputTextAnalyzerTest {

    @Test
    void apply() {
        String input = "one two three one two four one two three one two four one two five one two three one two five";
        Map<String, Map<Integer, List<String>>> result = new InputTextAnalyzer(new SentenceAnalyzer()).apply(input, 2);
        assertEquals(2, result.get("one two").size());
        assertTrue(result.get("one two").get(2).contains("five"));
        assertTrue(result.get("one two").get(2).contains("four"));
        assertTrue(result.get("one two").get(3).contains("three"));
    }
}