package com.boleo.internal.tests.bnpp;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class HackerRank {

    @Test
    public void test() throws Exception {
        String in = "Kinikinik";
        String input = in.toLowerCase();
        int median  = input.length() / 2;
        System.out.println(median);
        for (int i = 0; i < median; i++){
            if (input.charAt(i) != input.charAt(input.length() - i - 1)){
                System.out.println("False");
                break;
            }
        }

        System.out.println("True");
    }


}
