package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.soundexJW;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;

import java.util.ArrayList;
import java.util.List;

public class SoundexFuzzyRatio {
    private static FuzzySearch fuzzysearch ;
    public static double main(List<String> listtoken1, List<String> listtoken2, double weight, Soundex soundex, JaroWinklerSimilarity jaroWinkler) {
        List<String> token1 = new ArrayList<>(listtoken1);
        List<String> token2 = new ArrayList<>(listtoken2);

        token1.replaceAll(soundex::encode);
        token2.replaceAll(soundex::encode);

        double matching_per = token1.parallelStream()
                .flatMapToDouble(element -> token2.parallelStream()
                        .mapToDouble(element2 -> Math.round(fuzzysearch.ratio(element, element2)))
                )
                .average()
                .orElse(0.0); // Default if no matches
//                double score=(matching_per/100)*weight;
        return matching_per;

    }
    public static double Average(List<Double> numbers) {
        long count = numbers.stream().filter(v -> v != 0).count();
        double sum = numbers.stream().filter(v -> v != 0).mapToDouble(Double::doubleValue).sum();

        double out = (count > 0) ? (sum / count) : 0.0;
        return out;
    }
}
