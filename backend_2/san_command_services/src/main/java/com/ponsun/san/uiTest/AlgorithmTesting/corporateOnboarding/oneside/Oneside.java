package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.oneside;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import me.xdrop.fuzzywuzzy.FuzzySearch;


public class Oneside {
    public static double main(List<String> token1,List<String> token2,double weight) {

        // Remove elements from token1 that are empty or in the similar list
        token1.removeIf(s -> s.isEmpty() );
        // Remove elements from token2 that are empty or in the similar list
        token2.removeIf(s -> s.isEmpty() );


        LinkedHashSet<String> similar = new LinkedHashSet<>();
        int N = token1.size();
        double similarity = 0;
        boolean firstLetterMatch = false;

        // Match remaining tokens
        Iterator<String> iter1 =token1.iterator();
        while (iter1.hasNext()) {
            String t1 = iter1.next();
            Iterator<String> iter2 = token2.iterator();

            while (iter2.hasNext()) {
                String t2 = iter2.next();
                double sim1 = FuzzySearch.weightedRatio(t1, t2) / 100.0;

                if (sim1 == 1) {
                    similar.add(t1);
                    iter1.remove();
                    iter2.remove();
                    break;
                }
            }
        }

        // First-letter match optimization
        iter1 = token1.iterator();
        while (iter1.hasNext()) {
            String t1 = iter1.next();
            Iterator<String> iter2 = token2.iterator();

            while (iter2.hasNext()) {
                String t2 = iter2.next();
                if (t1.charAt(0) == t2.charAt(0) && (t1.length() == 1 || t2.length() == 1)) {
                    firstLetterMatch = true;
                    iter2.remove();
                    break;
                }
            }
        }

        // Compute final similarity
        if (token1.size() + similar.size() == N && token2.isEmpty() && firstLetterMatch) {
            similarity = 100;
        }

        return similarity ;
    }
}
