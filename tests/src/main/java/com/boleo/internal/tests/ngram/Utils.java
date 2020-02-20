package com.boleo.internal.tests.ngram;

import java.util.regex.Pattern;

public class Utils {

    public static String[] splitByNewLine(String text){
        if (text == null || text.isEmpty()){
            return new String[0];
        }
        return text.split("\\R+");
    }

    public static String[] splitIntoWords(String text){
        if (text == null || text.isEmpty()){
            return new String[0];
        }
        Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
        return pattern.split(text.replace("-", ""));
    }

}
