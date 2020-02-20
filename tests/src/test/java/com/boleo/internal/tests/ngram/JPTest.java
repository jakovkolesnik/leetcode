package com.boleo.internal.tests.ngram;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JPTest {

    @Test
    void apply() {
        String input = "Mary had a little lamb its fleece was white as snow;\n" +
                "And everywhere that Mary went, the lamb was sure to go.\n" +
                "It followed her to school one day, which was against the rule;\n" +
                "It made the children laugh and play, to see a lamb at school.\n" +
                "And so the teacher turned it out, but still it lingered near,\n" +
                "And waited patiently about till Mary did appear.\n" +
                "\"Why does the lamb love Mary so?\" the eager children cry;\"Why, Mary loves the lamb, you know\" the teacher did reply.\"";
        Map<String, Map<Integer, List<String>>> result = new InputTextAnalyzer(new SentenceAnalyzer()).apply(input, 1);
        Map<Integer, List<String>> predictions = result.get("the");
        String[] inputS = "2,the".split(",");
        if (inputS.length == 2) {
            Integer order = Integer.parseInt(inputS[0]);
            String term = inputS[1];
        } else {
            System.out.println("");
        }
    }

}
