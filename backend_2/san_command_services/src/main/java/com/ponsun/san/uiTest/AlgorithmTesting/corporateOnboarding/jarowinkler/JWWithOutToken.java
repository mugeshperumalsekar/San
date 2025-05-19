package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.jarowinkler;

import java.util.List;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
public class JWWithOutToken {

    public static double  main(String name1,String name2,double weight)  {
        JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();
        double matchingper = 0;

        matchingper = (double) Math.round(jaroWinkler.apply(name1,name2) * 100.0);

        return matchingper;
    }

    public static double Average(List<Double> numbers) {
        // System.out.println(numbers);
        long count = numbers.size(); // Count all elements, including 0s
        double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();
        return (count > 0) ? (sum / count) : 0.0;
    }

}