package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.LinkedHashSet;

public class CountryIndividualMatching {
    public static double  main(String input1,String input2) {
        if (input1.isBlank() || input2.isBlank()) {
            return 5; // Early exit for empty inputs
        }

        // Preprocess and tokenize
        String S1 = Preprocess.removeDuplicateWords_(input1);
        String S2 = Preprocess.removeDuplicateWords_(input2);
        LinkedHashSet<String> token1 = Functions.tokenizecountry_(S1);
        LinkedHashSet<String> token2 = Functions.tokenizecountry_(S2);

        // Use parallelStream for faster processing
        boolean found = token1.parallelStream().anyMatch(t1 ->
                token2.parallelStream().anyMatch(t2 ->
                        FuzzySearch.weightedRatio(t1, t2) / 100.0 == 1
                )
        );

        return found ? 10 : 0;

    }
}
