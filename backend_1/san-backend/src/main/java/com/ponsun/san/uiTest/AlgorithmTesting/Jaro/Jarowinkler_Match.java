package com.ponsun.san.uiTest.AlgorithmTesting.Jaro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import lombok.SneakyThrows;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
public class Jarowinkler_Match {
    //Jarowinkler_Match 80
//    public static int v = 0;
    @SneakyThrows
    public static double getJarowinklerMatching(String nametest1, String nametest2) {
        // Declare two names for comparison
//        String nametest1 ="shweta gupta ram";
//        String nametest2= "gupta shweta ram";

        JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();

        String name1 = removeDuplicateWords(nametest1);
        String name2 = removeDuplicateWords(nametest2);

        // Tokenize the names by spaces
        List<String> token1 = tokenizeString(name1);
        List<String> token2 = tokenizeString(name2);

        ArrayList<Double> sim_grea80 = new ArrayList<>();
        ArrayList<Double> sim_less80 = new ArrayList<>();

        Double[][] sim = new Double[token1.size()][token2.size()];

        // Create a thread pool for parallel processing
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Void>> futures = new ArrayList<>();

        // Multithreaded similarity calculation for rows
        for (int i = 0; i < token1.size(); i++) {
            final int idx = i;
            futures.add(executor.submit(new Callable<Void>() {
                @Override
                public Void call() {
                    for (int j = 0; j < token2.size(); j++) {
                        sim[idx][j] = (double) Math.round(jaroWinkler.apply(token1.get(idx), token2.get(j)) * 1e2);
                    }
                    return null;
                }
            }));
        }

        // Wait for all threads to finish
        for (Future<Void> future : futures) {
            future.get();
        }

        // Process rows after threads finish
        for (int i = 0; i < token1.size(); i++) {
            Double[] row = sim[i];
            int fl = 0;
            for (double v : row) {
                if (v >= 80) {
                    fl += 1;
                    sim_grea80.add(v);
                }
            }
            if (fl == 0) {
                List<Double> row_list = Arrays.asList(row);
                sim_less80.add(Average(row_list));
            }
        }

        // Multithreaded similarity calculation for columns
        futures.clear();
        for (int j = 0; j < token2.size(); j++) {
            final int idx = j;
            futures.add(executor.submit(new Callable<Void>() {
                @Override
                public Void call() {
                    Double[] column = Arrays.stream(sim).map(row -> row[idx]).toArray(Double[]::new);
                    int fl = 0;
                    for (double v : column) {
                        if (v >= 80) {
                            fl += 1;
                            break;
                        }
                    }
                    if (fl == 0) {
                        List<Double> col_list = Arrays.asList(column);
                        sim_less80.add(Average(col_list));
                    }
                    return null;
                }
            }));
        }

        // Wait for all threads to finish
        for (Future<Void> future : futures) {
            future.get();
        }

        // Shutdown the executor
        executor.shutdown();
        // Final similarity calculation

        double greater_80=0;
        if(sim_grea80.size()!=0) {
            greater_80=Average(sim_grea80);
        }
        double matchingper = 0;
        if (sim_less80.size() != 0) {
            double less_80 = Average(sim_less80);
            matchingper = ((greater_80 + less_80) / 2);
        } else {
            matchingper = greater_80;
        }
return matchingper;
//        System.out.println("Final matching score between " + nametest1 + " and " + nametest2 + " : " + matchingper);
    }

    // Method to calculate the average of a list of doubles
    public static double Average(List<Double> numbers) {
        double sum = 0;
        for (double v : numbers) {
            sum += v;
        }
        return (sum / numbers.size());
    }

    // Method to remove duplicate words from a string
    public static String removeDuplicateWords(String str) {
        str = str.toLowerCase();
        str = str.replaceAll("[^A-Za-z0-9\\s]", " ");
        String[] words = str.split("\\s+");
        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        return String.join(" ", uniqueWords);
    }

    // Method to tokenize a string by spaces
    public static List<String> tokenizeString(String str) {
        return Arrays.asList(str.split("\\s+"));
    }
}
