package com.boleo.internal.tests.ngram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class Test {
    private static final String INPUT_SEPARATOR = ",";
    private static final int EXPECTED_LENGTH = 2;

    /**
     * Iterate through each line of input.
     */
    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(reader);
        String line;

        String text = "Mary had a little lamb its fleece was white as snow;\n" +
                "And everywhere that Mary went, the lamb was sure to go.\n" +
                "It followed her to school one day, which was against the rule;\n" +
                "It made the children laugh and play, to see a lamb at school.\n" +
                "And so the teacher turned it out, but still it lingered near,\n" +
                "And waited patiently about till Mary did appear.\n" +
                "\"Why does the lamb love Mary so?\" the eager children cry;\"Why, Mary loves the lamb, you know\" the teacher did reply.\"";

        InputTextAnalyzer analyzer = new InputTextAnalyzer(new SentenceAnalyzer());
        Predictor predictor = new Predictor();

        //while ((line = in.readLine()) != null) {
        for (int i = 0; i < args.length; i++) {
            //String[] input = line.split(INPUT_SEPARATOR);
            String[] input = args[i].split(INPUT_SEPARATOR);
            if (input.length == EXPECTED_LENGTH) {
                Integer order = Integer.parseInt(input[0]);
                String term = input[1].replace("-", "").trim();
                String result = predictor.apply(analyzer.apply(text, order), term);
                System.out.println(result);
            } else {
                System.out.println("");
            }
        }
    }

    static class Predictor implements BiFunction<Map<String, Map<String, Integer>>, String, String> {

        public String apply(Map<String, Map<String, Integer>> predictions, String term) {
            Map<String, Map<Integer, List<String>>> resultByScore = resultByScore(predictions);
            if (!resultByScore.containsKey(term)) {
                return "";
            }
            return analyzeScore(resultByScore.get(term));
        }

        private String analyzeScore(Map<Integer, List<String>> predictions) {
            Integer total = 0;
            List<Integer> scores = new ArrayList<>();
            for (Integer score : predictions.keySet()) {
                total = total + score * predictions.get(score).size();
                scores.add(score);
            }
            scores.sort(Collections.reverseOrder());
            return buildResultString(total, scores, predictions);
        }

        private String buildResultString(Integer total, List<Integer> sortedScores, Map<Integer, List<String>> predictions) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < sortedScores.size(); i++) {
                Integer score = sortedScores.get(i);
                Double predictionScore = score.doubleValue() / total.doubleValue();
                List<String> words = predictions.get(score);
                words.sort(String::compareToIgnoreCase);
                for (int j = 0; j < words.size(); j++) {
                    String word = words.get(j);
                    result.append(word).append(",");
                    result.append(String.format("%.3f", predictionScore));
                    if (j < words.size() - 1) {
                        result.append(";");
                    }
                }
                if (i < sortedScores.size() - 1) {
                    result.append(";");
                }
            }
            return result.toString();
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

    /**
     * Splits text into lines, and analyzes each line separately, then merges results.
     * Uses regex to split lines and strip non-word characters (Hyphen is deleted, so N-gram becomes Ngram).
     */
    static class InputTextAnalyzer implements BiFunction<String, Integer, Map<String, Map<String, Integer>>> {
        private final SentenceAnalyzer sentenceAnalyzer;

        InputTextAnalyzer(SentenceAnalyzer sentenceAnalyzer) {
            this.sentenceAnalyzer = sentenceAnalyzer;
        }

        @Override
        public Map<String, Map<String, Integer>> apply(String text, Integer order) {
            if (text == null) {
                throw new IllegalArgumentException("Input text cannot be null");
            }

            String[] lines = splitByNewLine(text);

            Map<String, Map<String, Integer>> predictions = new HashMap<>();

            for (String line : lines) {
                sentenceAnalyzer.analyzeSentence(splitIntoWords(line), order, predictions);
            }

            return predictions;
        }

        private String[] splitByNewLine(String text) {
            if (text == null || text.isEmpty()) {
                return new String[0];
            }
            return text.split("\\R+");
        }

        private String[] splitIntoWords(String text) {
            if (text == null || text.isEmpty()) {
                return new String[0];
            }
            Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
            return pattern.split(text.replace("-", ""));
        }
    }

    static class SentenceAnalyzer {

        void analyzeSentence(String[] sentence, int order, Map<String, Map<String, Integer>> result) {
            if (order < 2) {
                throw new IllegalArgumentException("N-Gram order must be greater than 1");
            }
            if (sentence == null) {
                throw new IllegalArgumentException("Input sentence cannot be null");
            }
            if (result == null) {
                throw new IllegalArgumentException("Result map cannot be null");
            }
            for (int i = 0; i < sentence.length - (order - 1); i++) {
                StringBuilder keyBuilder = new StringBuilder();
                int j = 1;
                keyBuilder.append(sentence[i]);
                while (j < order - 1) {
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
}
