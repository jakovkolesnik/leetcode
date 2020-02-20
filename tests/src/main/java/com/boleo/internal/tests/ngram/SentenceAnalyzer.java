package com.boleo.internal.tests.ngram;

import java.util.HashMap;
import java.util.Map;

public class SentenceAnalyzer {

    public void analyzeSentence(String[] sentence, int order, Map<String, Map<String, Integer>> result) {
        if (order < 1) {
            throw new IllegalArgumentException("N-Gram order must be greater than 0");
        }
        if (sentence == null) {
            throw new IllegalArgumentException("Input sentence cannot be null");
        }
        if (result == null) {
            throw new IllegalArgumentException("Result map cannot be null");
        }
        for (int i = 0; i < sentence.length - order; i++) {
            StringBuilder keyBuilder = new StringBuilder();
            int j = 1;
            keyBuilder.append(sentence[i]);
            while (j < order) {
                keyBuilder.append(" ");
                keyBuilder.append(sentence[i + j]);
                j = j + 1;
            }
            String nextWord = sentence[i + j];
            String key = keyBuilder.toString();
            addPrediction(key, nextWord, result);
        }
    }

    private void addPrediction(String key, String nextWord, Map<String, Map<String, Integer>> result) {
        if (!result.containsKey(key)) {
            result.put(key, new HashMap<>());
        }
        Map<String, Integer> predictions = result.get(key);
        if (predictions.containsKey(nextWord)) {
            predictions.put(nextWord, predictions.get(nextWord) + 1);
        } else {
            predictions.put(nextWord, 1);
        }
    }
}
