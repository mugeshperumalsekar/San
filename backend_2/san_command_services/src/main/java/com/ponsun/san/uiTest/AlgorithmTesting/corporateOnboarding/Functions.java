package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import me.xdrop.fuzzywuzzy.FuzzySearch;


public class Functions {

//    public static LinkedHashSet<String> tokenizeString(String str) {
//        String[] tokens = str.contains(" ") ? str.split(" ") : new String[]{str};
//        return new LinkedHashSet<>(Arrays.asList(tokens));
//    }


    public static ArrayList<String> tokenizeString_Array(String str) {
        return new ArrayList<>(Arrays.asList(str.replaceAll("\\p{Zs}+", " ").trim().split("\\s+")));
    }

    public static LinkedHashSet<String> dateTokenize(String str) {
        str = str.replaceAll("--", "-");
        String[] tokens = str.contains(",") ? str.split(",") : new String[]{str};
        return new LinkedHashSet<>(Arrays.asList(tokens));
    }

    public static LinkedHashSet<String> tokenizecountry_(String str) {
        String[] tokens = str.contains(",") ? str.split(",") : new String[]{str};
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].replaceAll("[^A-Za-z0-9\s]", " ");
        }
        return new LinkedHashSet<>(Arrays.asList(tokens));
    }
//
//    public static LinkedHashSet<String> tokenizeString_twoside(String str) {
//        String[] tokens = str.contains(" ") ? str.split(" ") : new String[]{str};
//        return new LinkedHashSet<>(Arrays.asList(tokens));
//    }

    public static Map<String, List<String>> similarfinding(List<String>betoken1,List<String> betoken2) {
        List<String> token1 = new ArrayList<>(betoken1);
        List<String> token2 = new ArrayList<>(betoken2);

        if (token1.size() > token2.size()) {
            List<String> temp = token1;
            token1 = token2;
            token2 = temp;
        }
        ArrayList<String> similar = new ArrayList<>();
        ArrayList<String> similar1 = new ArrayList<>();
        ArrayList<String> similar2 = new ArrayList<>();

        double sim=0;int l1=0,l2=0;
        for( l1=0;l1<token1.size();l1++) {
            for( l1=0;l1<token1.size();l1++) {
                sim= FuzzySearch.weightedRatio(token1.get(l1),token2.get(l2))/1e2;
                if ( sim==1){
                    similar.add(token1.get(l1));
                    token1.remove(token1.indexOf(similar.get(similar.size()-1)));
                    token2.remove(token2.indexOf(similar.get(similar.size()-1)));
                }
            }
        }


        sim=0; l1=0;l2=0;   int incr=0;
        while(l1<token1.size() && l2<token2.size()) {
            l2=0;
            while(l2<token2.size() && l1<token1.size()) {
                incr=0;
                sim= FuzzySearch.weightedRatio(token1.get(l1),token2.get(l2))/1e2;
                if ( sim==1){
                    similar.add(token1.get(l1));
                    token1.remove(token1.indexOf(similar.get(similar.size()-1)));
                    token2.remove(token2.indexOf(similar.get(similar.size()-1)));
                    l2=0;
                } else if(token1.get(l1).contains(token2.get(l2))&& token2.get(l2).length()>1 ) {
                    similar2.add(token2.get(l2));
                    int stindex=token2.get(l2).indexOf(similar2.get(similar2.size()-1));
                    int enindex= stindex + similar2.get(similar2.size()-1).length() - 1;
                    token2.set(l2, token2.get(l2).substring(0, stindex) + token2.get(l2).substring(enindex + 1));
                    stindex=token1.get(l1).indexOf(similar2.get(similar2.size()-1));
                    enindex= stindex + similar2.get(similar2.size()-1).length() - 1;
                    token1.set(l1, token1.get(l1).substring(0, stindex) + token1.get(l1).substring(enindex + 1));
                    incr++;
                }
                else if(token1.get(l1).contains(new StringBuilder(token2.get(l2)).reverse().toString())&& token2.get(l2).length()>1 ) {

                    similar2.add(new StringBuilder(token2.get(l2)).reverse().toString());
                    int stindex=new StringBuilder(token2.get(l2)).reverse().toString().indexOf(similar2.get(similar2.size()-1));
                    int enindex= stindex + similar2.get(similar2.size()-1).length() - 1;
                    token2.set(l2, new StringBuilder(token2.get(l2)).reverse().toString().substring(0, stindex) + token2.get(l2).substring(enindex + 1));
                    stindex=token1.get(l1).indexOf(similar2.get(similar2.size()-1));
                    enindex= stindex + similar2.get(similar2.size()-1).length() - 1;
                    token1.set(l1, token1.get(l1).substring(0, stindex) + token1.get(l1).substring(enindex + 1));
                    incr++;
                }


                else if(token2.get(l2).contains(token1.get(l1))&& token1.get(l1).length()>1) {
                    similar1.add(token1.get(l1));
                    int stindex=token1.get(l1).indexOf(similar1.get(similar1.size()-1));
                    int enindex= stindex + similar1.get(similar1.size()-1).length() - 1;
                    token1.set(l1, token1.get(l1).substring(0, stindex) + token1.get(l1).substring(enindex + 1));
                    stindex=token2.get(l2).indexOf(similar1.get(similar1.size()-1));
                    enindex= stindex + similar1.get(similar1.size()-1).length() - 1;
                    token2.set(l2, token2.get(l2).substring(0, stindex) + token2.get(l2).substring(enindex + 1));
                    incr++;
                }
                else if(token2.get(l2).contains(new StringBuilder(token1.get(l1)).reverse().toString())&& token1.get(l1).length()>1) {

                    similar1.add(new StringBuilder(token1.get(l1)).reverse().toString());


                    int stindex=new StringBuilder(token1.get(l1)).reverse().toString().indexOf(similar1.get(similar1.size()-1));

                    int enindex= stindex + similar1.get(similar1.size()-1).length() - 1;
                    token1.set(l1, new StringBuilder(token1.get(l1)).reverse().toString().substring(0, stindex) + token1.get(l1).substring(enindex + 1));
                    stindex=token2.get(l2).indexOf(similar1.get(similar1.size()-1));
                    enindex= stindex + similar1.get(similar1.size()-1).length() - 1;
                    token2.set(l2, token2.get(l2).substring(0, stindex) + token2.get(l2).substring(enindex + 1));
                    incr++;
                }

                else {
                    incr++;
                }
                if(incr>0) { l2++;}
            }
            l2=0;
            if(incr>0) { l1++;}

        }


//
//  System.out.println("token1 "+token1);
//  System.out.println("token2 "+token2);
//  System.out.println("similar "+similar);
//  System.out.println("similar1 "+similar1);
//  System.out.println("similar2 "+similar2);

        Map<String, List<String>> out = new HashMap<>();
        out.put("token1", token1);out.put("token2", token2);
        out.put("similar", similar);
        out.put("similar1", similar1);    out.put("similar2", similar2);
        return out;

    }




    public static double percentage(double input) {
        // Hardcoded mapping
        Map<Double, Double> percentageMap = new HashMap<>();
        percentageMap.put(60.0, 60.00);
        percentageMap.put(61.0, 60.50);
        percentageMap.put(62.0, 61.00);
        percentageMap.put(63.0, 61.50);
        percentageMap.put(64.0, 62.00);
        percentageMap.put(65.0, 62.50);
        percentageMap.put(66.0, 63.00);
        percentageMap.put(67.0, 63.50);
        percentageMap.put(68.0, 64.00);
        percentageMap.put(69.0, 64.50);
        percentageMap.put(70.0, 65.00);
        percentageMap.put(71.0, 65.50);
        percentageMap.put(72.0, 66.00);
        percentageMap.put(73.0, 66.50);
        percentageMap.put(74.0, 67.00);
        percentageMap.put(75.0, 67.50);
        percentageMap.put(76.0, 68.00);
        percentageMap.put(77.0, 68.50);
        percentageMap.put(78.0, 69.00);
        percentageMap.put(79.0, 69.50);
        percentageMap.put(80.0, 70.00);
        percentageMap.put(81.0, 70.50);
        percentageMap.put(82.0, 71.00);
        percentageMap.put(83.0, 71.50);
        percentageMap.put(84.0, 72.00);
        percentageMap.put(85.0, 72.50);
        percentageMap.put(86.0, 73.00);
        percentageMap.put(87.0, 73.50);
        percentageMap.put(88.0, 74.00);
        percentageMap.put(89.0, 74.50);
        percentageMap.put(90.0, 75.00);
        percentageMap.put(91.0, 75.50);
        percentageMap.put(92.0, 76.00);
        percentageMap.put(93.0, 76.50);
        percentageMap.put(94.0, 77.00);
        percentageMap.put(95.0, 77.50);
        percentageMap.put(96.0, 78.00);
        percentageMap.put(97.0, 78.50);
        percentageMap.put(98.0, 79.00);
        percentageMap.put(99.0, 79.50);
        percentageMap.put(100.0, 80.00);

        // Test input
        return percentageMap.get(input);

    }


    public static Set<String> tokenize_jw(String str) {
        return new LinkedHashSet<>(Arrays.asList(str.split("\\s+")));
    }

    public static LinkedHashSet<String> tokenizeString_id(String str) {
        String[] tokens = str.contains(",") ? str.split(",") : new String[]{str};
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].replaceAll("[^A-Za-z0-9 ]", "").replaceAll("\\s+", "");
        }
        return new LinkedHashSet<>(Arrays.asList(tokens));
    }
}