package com.ponsun.san.uiTest.AlgorithmTesting.Oneside;
import lombok.SneakyThrows;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Oneside {
    public static ArrayList<String> token;
    public static int f1;
        @SneakyThrows
        public static double onesideMatching(String nametest1, String nametest2) {

            String name1 = removeDuplicateWords(nametest1);
            String name2 = removeDuplicateWords(nametest2);

            // Tokenize the names by spaces
            ArrayList<String> token1 = tokenizeString(name1);
            ArrayList<String> token2 = tokenizeString(name2);

            // Find the similar tokens using multithreading
            ArrayList<String> similar = new ArrayList<>();

            double similarity = 0;
            int N = 0;f1=0;

            // Apply the size condition check before matching
            if (token1.size() == token2.size()) {
                N = token1.size();  // Assign size to N if the tokens are of equal length

                // Create a thread pool for parallel processing
                ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                List<Future<Void>> futures = new ArrayList<>();

                // Compare tokens using multithreading
                for (int l1 = 0; l1 < token1.size(); l1++) {
                    final int idx = l1;
                    futures.add(executor.submit(new Callable<Void>() {
                        @Override
                        public Void call() {
                            double sim = 0;
                            for (int l2 = 0; l2 < token2.size(); l2++) {
                                if(token1.get(idx).length()==1||token2.get(l2).length()==1)
                                    f1++;
                                sim = FuzzySearch.weightedRatio(token1.get(idx), token2.get(l2)) / 1e2;
                                if (sim == 1) {
                                    synchronized (similar) { // Thread-safe addition to the list
                                        similar.add(token1.get(idx));
                                    }
                                    break;
                                }
                            }
                            return null;
                        }
                    }));
                }

                // Wait for all threads to complete
                for (Future<Void> future : futures) {
                    future.get();
                }

                // Shutdown the executor
                executor.shutdown();

                // Remove the matching elements
                token1.removeAll(similar);
                token2.removeAll(similar);

                // Further matching logic (handling the first-letter match)
                for (int l1 = 0; l1 < token1.size(); l1++) {
                    int l2 = 0;
                    while (!token2.isEmpty() && l2 < token2.size()) {
                        if (token2.get(l2).charAt(0) == token1.get(l1).charAt(0) &&
                                (token1.get(l1).length() == 1 || token2.get(l2).length() == 1)) {
                            token2.remove(l2);
                            break;
                        } else {
                            l2++;
                        }
                    }
                }

                // If all tokens matched
                if (token1.size() + similar.size() == N && token2.size() == 0 && f1!=0) {
                    similarity = 100;
                }
            }

//            System.out.println("Final matching score between " + nametest1 + " and " + nametest2 + " : " + similarity);

//        double en=System.currentTimeMillis()-st;
//        System.out.println(en);
return similarity;
        }

    public static String removeDuplicateWords(String str) {
        str = str.toLowerCase();
        str = str.replaceAll("[^A-Za-z0-9\\s]", " ");
        String[] words = str.split("\\s+");
        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        return String.join(" ", uniqueWords);
    }

    // Method to tokenize a string by spaces
    public static ArrayList<String> tokenizeString(String str) {
        String[] tokens;
        if (str.contains(" ")) { // If the string contains spaces, split by space
            tokens = str.split(" ");
        } else { // Otherwise, return the single string as an array
            tokens = new String[]{str};
        }
        return new ArrayList<>(Arrays.asList(tokens));
    }
}
