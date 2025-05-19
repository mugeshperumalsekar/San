package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.fuzzyWR;

import java.util.List;


public class FuzzyWRWithOutToken {
    public static double main(String name1,String name2,double weight) {
        double matchingper = 0;

        matchingper = (double) Math.round(me.xdrop.fuzzywuzzy.FuzzySearch.weightedRatio(name1,name2)) ;

        return matchingper;
    }

    public static double Average(List<Double> numbers) {
        long count = numbers.size(); // Count all elements, including 0s
        double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();

        return (count > 0) ? (sum / count) : 0.0;
    }

}
