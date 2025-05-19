package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding;

import java.util.*;

public class Preprocess {
//
//    public static String datafilter_Init(String str) {
//        return String.join(" ", findUniqueWords(removeUnwantedChars(convertToLower(str))));
//    }
//    private static String convertToLower(String input) {
//        return input.toLowerCase();
//    }
//    private static String removeUnwantedChars(String input) {
//        return input.replaceAll("[^A-Za-z0-9\\s]", " ");
//    }
//    private static List<String> findUniqueWords(String input) {
//        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(input.split("\\s+")));
//        return new ArrayList<>(uniqueWords);
//    }
//    public static String removeDuplicateWords_(String str) {
//        str = str.toLowerCase();
//        String[] words = str.split("\\s+");
//        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
//        return String.join(" ", uniqueWords);
//    }
//
//    public static double findlength(String str) {
//        return str.replaceAll("\\s+", "").length();
//    }
//
//    public static String removeDuplicate_twoside(String str) {
//        str = str.toLowerCase();
//        str = str.replaceAll("[^A-Za-z0-9\\s]", " ");
//        String[] words = str.split("\\s+");
//        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
//        return String.join(" ", uniqueWords);
//    }
//
//    // Method to tokenize a string by spaces
//    public static Set<String> tokenizeString_twoside(String str) {
//        String[] tokens = str.split("\\s+"); // Tokenize by spaces
//        return new LinkedHashSet<>(Arrays.asList(tokens)); // Store as LinkedHashSet
//    }
//
//    public static String removeDuplicateWords_id(String str) {
//        str = str.toLowerCase();
//        String[] words = str.split("\\s+");
//        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
//        return String.join(" ", uniqueWords);
//    }
//}
//////////////////////////////////////////////
//    public static String datafilter_Init(String str) {
//        str = ConvertLower(str);
//        str = RemoveUnwantedChar(str);
//        return FindUnique_Init(str);
//    }
//
//    static String ConvertLower(String input) {
//        return input.toLowerCase();
//    }
//
//    static String RemoveUnwantedChar(String input) {
//        return input.replaceAll("[^A-Za-z0-9\\s]", "");
//    }
//
//    static String FindUnique_Init(String input) {
//        String[] words = input.split("\\s+");
//        ArrayList<String> uniqueWords = new ArrayList<String>();
//        for (String word : words) {
//            if (word.length() == 1) {
//                uniqueWords.add(word);
//            }else if( !uniqueWords.contains(word)) {
//                uniqueWords.add(word);
//            }
//        }
//        return String.join(" ", uniqueWords);
//    }
//
//    public static String removeDuplicateWords_(String str) {
//        str = str.toLowerCase();
//        String[] words = str.split("\\s+");
//        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
//        return String.join(" ", uniqueWords);
//    }
//
//    public static double findlength(String str) {
//        return str.replaceAll("\\s+", "").length();
//    }
//
//    public static Set<String> removeDuplicate_twoside(String str) {
//        str = str.toLowerCase();
//        str = str.replaceAll("[^A-Za-z0-9\\s]", " ");
//        String[] words = str.split("\\s+");
//        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
//        return uniqueWords;
//    }
//
//
//    public static String removeDuplicateWords_id(String str) {
//        str = str.toLowerCase();
//        String[] words = str.split("\\s+");
//        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
//        return String.join(" ", uniqueWords);
//    }

    public static String datafilter_Init(String str) {
        str = ConvertLower(str);
        str = RemoveUnwantedChar(str);
        return FindUnique_Init(str);
    }

    static String ConvertLower(String input) {
        return input.toLowerCase();
    }

    static String RemoveUnwantedChar(String input) {
        return input.replaceAll("[^A-Za-z0-9\\s]", " ");
    }

    static String FindUnique_Init(String input) {
        String[] words = input.split("\\s+");
        ArrayList<String> uniqueWords = new ArrayList<String>();
        for (String word : words) {
            if (word.length() == 1) {
                uniqueWords.add(word);
            }else if( !uniqueWords.contains(word)) {
                uniqueWords.add(word);
            }
        }
        return String.join(" ", uniqueWords);
    }

    public static String removeDuplicateWords_(String str) {
        str = str.toLowerCase();
        String[] words = str.split("\\s+");
        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        return String.join(" ", uniqueWords);
    }

    public static double findlength(String str) {
        return str.replaceAll("\\s+", "").length();
    }

    public static Set<String> removeDuplicate_twoside(String str) {
        str = str.toLowerCase();
        str = str.replaceAll("[^A-Za-z0-9\\s]", " ");
        String[] words = str.split("\\s+");
        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        return uniqueWords;
    }


    public static String removeDuplicateWords_id(String str) {
        str = str.toLowerCase();
        String[] words = str.split("\\s+");
        Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
        return String.join(" ", uniqueWords);
    }
}
