package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.jarowinkler;

import java.util.*;
import java.util.stream.IntStream;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import java.util.ArrayList;
public class JaroWinkler {
        public static double  main(String name1,String name2,List<String> token1,List<String> token2,double weight,JaroWinklerSimilarity jaroWinkler)  {

                Double[][] sim = new Double[token1.size()][token2.size()];
                double matchingper = 0;

                // Create a thread pool for parallel processing
                IntStream.range(0, token1.size()).parallel().forEach(i -> {
                        String t1 = token1.get(i);
                        for (int j = 0; j < token2.size(); j++) {
                                String t2 = token2.get(j);
                                sim[i][j] = (double) Math.round(jaroWinkler.apply(t1, t2) * 100.0);
//              // System.out.println(i +" "+j+" "+ sim[i][j] );
                        }
                });

                if(token1.size()>token2.size()) {
                        // Row-wise max values
                        List<Double> maxValuesList_row = new ArrayList<>();

                        Arrays.stream(sim).forEach(row -> {
                                double maxVal = Arrays.stream(row).mapToDouble(Double::doubleValue).max().orElse(Double.MIN_VALUE);
                                maxValuesList_row.add(maxVal);
                        });
                        matchingper = Average(maxValuesList_row);

//         // System.out.println("Max Values List (Row-wise): " + maxValuesList_row);

                } else {
                        // Column-wise max values
                        int cols = sim[0].length;
                        List<Double> maxValuesList_col = new ArrayList<>(Collections.nCopies(cols, Double.MIN_VALUE));

                        IntStream.range(0, cols).forEach(col -> {
                                double maxVal = Arrays.stream(sim)
                                        .mapToDouble(row -> row[col])
                                        .max()
                                        .orElse(Double.MIN_VALUE);
                                maxValuesList_col.set(col, maxVal);  // Correctly updates list
                        });
                        matchingper = Average(maxValuesList_col);
//         // System.out.println("Max Values List (Column-wise): " + maxValuesList_col);  // Corrected list name
                }

                double matchingper2 = (double) Math.round(jaroWinkler.apply(name1,name2) * 100.0);
                if(matchingper<matchingper2) {
                        matchingper= matchingper2;
                }

//                double score=(matchingper/100)*weight;
                return matchingper;
        }

        public static double Average(List<Double> numbers) {
                long count = numbers.size(); // Count all elements, including 0s
                double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();
                return (count > 0) ? (sum / count) : 0.0;
        }

}