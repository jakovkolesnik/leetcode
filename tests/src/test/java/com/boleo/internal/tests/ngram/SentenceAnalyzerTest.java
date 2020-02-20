package com.boleo.internal.tests.ngram;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SentenceAnalyzerTest {

    @Test
    void analyzeSentence() {
        String input = "N-grams can be estimated";;
        Map<String, Map<String, Integer>>result = new HashMap<>();
        new SentenceAnalyzer().analyzeSentence(Utils.splitIntoWords(input), 2, result);
        assertEquals(2, result.size());
        assertEquals(1, result.get("Ngrams can").size());
        assertEquals(1, result.get("can be").size());
        assertEquals(Integer.valueOf(1), result.get("Ngrams can").get("be"));
        assertEquals(Integer.valueOf(1), result.get("can be").get("estimated"));
    }

    @Test
    void analyzeSentenceMultiplePredictions() {
        String input = "one two three one two four one two three one two four one two five one two three";
        Map<String, Map<String, Integer>>result = new HashMap<>();
        new SentenceAnalyzer().analyzeSentence(Utils.splitIntoWords(input), 2, result);
        assertEquals(7, result.size());
        assertEquals(3, result.get("one two").size());
        assertEquals(Integer.valueOf(3), result.get("one two").get("three"));
        assertEquals(Integer.valueOf(2), result.get("one two").get("four"));
        assertEquals(Integer.valueOf(1), result.get("one two").get("five"));
    }
}