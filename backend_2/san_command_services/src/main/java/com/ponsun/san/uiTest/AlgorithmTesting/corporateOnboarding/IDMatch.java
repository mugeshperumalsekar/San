package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding;

import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.LinkedHashSet;

public class IDMatch {
    public static double main(String str1, String str2) {
        if (str1.isBlank() || str2.isBlank()) return 0;

        // Remove duplicate words
        String S1 = Preprocess.removeDuplicateWords_id(str1);
        String S2 = Preprocess.removeDuplicateWords_id(str2);

        // Tokenize
        LinkedHashSet<String> token1 = Functions.tokenizeString_id(S1);
        LinkedHashSet<String> token2 = Functions.tokenizeString_id(S2);

        // **Parallel Matching with Early Termination**
        return token1.parallelStream()
                .flatMap(t1 -> token2.parallelStream()
                        .map(t2 -> GetMatching(t1, t2))
                )
                .filter(sim -> sim == 100) // **Stop early if 100% match is found**
                .findFirst()
                .orElse(0.0); // If no match found, return 0
    }

    public static double GetMatching(String input1, String input2) {
        input1 = normalizeString(input1);
        input2 = normalizeString(input2);
        double sim = FuzzySearch.weightedRatio(input1, input2);

        // **Fast Exact Match Check (Skips FuzzySearch if Lengths Match)**
        if (sim != 100 && input1.length() == input2.length() && input1.equals(input2)) {
            return 100;
        }
        return sim;
    }

    private static String normalizeString(String str) {
        return str.replace('o', '0').replace('i', '1');
    }
}

