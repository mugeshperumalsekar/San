package com.ponsun.san.uiTest.AlgorithmTesting.Fuzzy;

import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Fuzzy_WeightedRatio {

    public static double Fuzzy_WeightedRatio(String nametest1, String nametest2) {

        String name1 = removeDuplicateWords(nametest1);
        String name2 = removeDuplicateWords(nametest2);

        // Tokenize the names by spaces
        List<String> token1 = tokenizeString(name1);
        List<String> token2 = tokenizeString(name2);

        // Ensure token1 is the larger or equal-sized list
        if (token2.size() < token1.size()) {
            List<String> temp = new ArrayList<>(token2);
            token2 = new ArrayList<>(token1);
            token1 = temp;
        }

        List<Double> similarity = new ArrayList<>();

        for (String token2Element : token2) {
            // Use an unmodified copy of token1
            List<String> token1Copy = new ArrayList<>(token1);
            List<Double> sim = IntStream.range(0, token1Copy.size())
                    .mapToObj(j -> {
                        double score = (double) Math.round(FuzzySearch.weightedRatio(token2Element, token1Copy.get(j)));
//                    System.out.println("Similarity score between " + token2Element + " and " + token1Copy.get(j) + " is :" + score);
                        return score;
                    })
                    .collect(Collectors.toList());
//            System.out.println("-----------------------------------------------");

            boolean contains100 = sim.contains(100.0);
            if (contains100) {
                similarity.add(100.0);
            } else {
                List<Double> filteredScores = sim.stream()
                        .filter(score -> score >= 80)
                        .collect(Collectors.toList());

                if (filteredScores.isEmpty()) {
                    similarity.addAll(sim);
                } else {
                    similarity.addAll(filteredScores);
                }
            }
        }

        double matchingPer = Average(similarity);
        return matchingPer;

    }

    public static double Average(List<Double> numbers) {
        return numbers.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public static String removeDuplicateWords(String str) {
        return Arrays.stream(str.toLowerCase().trim().split("\\s+"))
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .stream()
                .collect(Collectors.joining(" "));
    }

    // Method to tokenize a string by spaces
    public static List<String> tokenizeString(String str) {
        return Arrays.asList(str.split("\\s+"));
    }
}
