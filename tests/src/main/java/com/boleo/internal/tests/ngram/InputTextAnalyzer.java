package com.boleo.internal.tests.ngram;

import java.util.*;
import java.util.function.BiFunction;

public class InputTextAnalyzer implements BiFunction<String, Integer, Map<String, Map<Integer, List<String>>>> {
    private final SentenceAnalyzer sentenceAnalyzer;

    public InputTextAnalyzer(SentenceAnalyzer sentenceAnalyzer) {
        this.sentenceAnalyzer = sentenceAnalyzer;
    }

    @Override
    public Map<String, Map<Integer, List<String>>> apply(String text, Integer order) {
        if (text == null) {
            throw new IllegalArgumentException("Input text cannot be null");
        }

        String[] lines = Utils.splitByNewLine(text);

        Map<String, Map<String, Integer>> predictions = new HashMap<>();

        for (String line : lines) {
            sentenceAnalyzer.analyzeSentence(Utils.splitIntoWords(line), order, predictions);
        }

        return resultByScore(predictions);
    }

    private Map<String, Map<Integer, List<String>>> resultByScore(Map<String, Map<String, Integer>> predictions) {
        Map<String, Map<Integer, List<String>>> result = new HashMap<>();
        for (String key : predictions.keySet()) {
            Map<String, Integer> prediction = predictions.get(key);
            if (!prediction.isEmpty()) {
                if (!result.containsKey(key)) {
                    result.put(key, new HashMap<>());
                }
                for (String word : prediction.keySet()) {
                    Integer score = prediction.get(word);
                    if (!result.get(key).containsKey(score)) {
                        result.get(key).put(score, new ArrayList<>());
                    }
                    result.get(key).get(score).add(word);
                }
            }
        }

        return result;
    }
}
