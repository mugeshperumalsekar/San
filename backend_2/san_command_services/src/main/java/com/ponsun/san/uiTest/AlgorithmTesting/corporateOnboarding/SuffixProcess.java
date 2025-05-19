package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class SuffixProcess {
    public static LinkedHashSet<String> ReadSuff() {
        LinkedHashSet<String> suffixes = new LinkedHashSet<>();
        //String filePath = "src/main/resources/data/data.txt";  // Absolute path during development

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data/data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                suffixes.add(line.toLowerCase());		            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suffixes;
    }


    public static String RemoveSuff(LinkedHashSet<String> suffixes, String Input) {
        // Split input into words
        String[] words = Input.split("\\s+");
        ArrayList<String> resultWords = new ArrayList<>(Arrays.asList(words));

        // Sort suffixes by length in descending order to prioritize the longest match
        ArrayList<String> sortedSuffixes = new ArrayList<>(suffixes);
        sortedSuffixes.sort((a, b) -> Integer.compare(b.length(), a.length()));
        // Check for the longest matching suffix and remove it
        for (String suffix : sortedSuffixes) {
            if (Input.endsWith(suffix)) { // Ensure we remove only suffixes at the end
                String[] suffixWords = suffix.split("\\s+");
                int suffixLength = suffixWords.length;

                // Remove the suffix from the result words
                int startIdx = resultWords.size() - suffixLength;
                if (startIdx >= 0 && String.join(" ", resultWords.subList(startIdx, resultWords.size())).equals(suffix)) {
                    resultWords.subList(startIdx, resultWords.size()).clear();
                    break; // Stop after removing the first matching suffix
                }
            }
        }

        // Join remaining words
        return String.join(" ", resultWords);
    }



    public static String RemoveSuff_ind(String input) {
        // Define suffixes
        List<String> suffixes = Arrays.asList("w o", "c o", "d o", "s o");

        // Split the input into words
        String[] words = input.split("\\s+");

        // Iterate through the words to find the suffix
        for (int i = 0; i < words.length - 1; i++) {
            String suffixCandidate = words[i] + " " + words[i + 1]; // Form a two-word suffix
            if (suffixes.contains(suffixCandidate)) {
                return String.join(" ", Arrays.copyOfRange(words, 0, i)); // Remove suffix and all trailing words
            }
        }
        return input; // Return original input if no suffix is found
    }


}

