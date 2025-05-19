package com.ponsun.san.uiTest.AlgorithmTesting.ExactMatch;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExactMatch {
    public static ArrayList<String> token;
    public static double ExactMatching(String nametest1,String nametest2) {

        // Tokenize the names by spaces
        ArrayList<String> token1 = tokenizeString(nametest1);
        ArrayList<String> token2 = tokenizeString(nametest2);

        double matchcount = 0;
        double totalcount1 = findlength(nametest1);
        double totalcount2 = findlength(nametest2);

        // Find the similar tokens
        ArrayList<String> similar = new ArrayList<>();
        ArrayList<String> similar1 = new ArrayList<>();
        ArrayList<String> similar2 = new ArrayList<>();

        double sim=0;int l1=0 ,l2=0;   int incr=0;
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
                } else if(token1.get(l1).contains(token2.get(l2))&& token2.get(l2).length()>1) {
                    similar2.add(token2.get(l2));
                    int stindex=token2.get(l2).indexOf(similar2.get(similar2.size()-1));
                    int enindex= stindex + similar2.get(similar2.size()-1).length() - 1;
                    token2.set(l2, token2.get(l2).substring(0, stindex) + token2.get(l2).substring(enindex + 1));
                    stindex=token1.get(l1).indexOf(similar2.get(similar2.size()-1));
                    enindex= stindex + similar2.get(similar2.size()-1).length() - 1;
                    token1.set(l1, token1.get(l1).substring(0, stindex) + token1.get(l1).substring(enindex + 1));
                    incr++;
                } else if(token2.get(l2).contains(token1.get(l1))&& token1.get(l1).length()>1) {
                    similar1.add(token1.get(l1));
                    int stindex=token1.get(l1).indexOf(similar1.get(similar1.size()-1));
                    int enindex= stindex + similar1.get(similar1.size()-1).length() - 1;
                    token1.set(l1, token1.get(l1).substring(0, stindex) + token1.get(l1).substring(enindex + 1));
                    stindex=token2.get(l2).indexOf(similar1.get(similar1.size()-1));
                    enindex= stindex + similar1.get(similar1.size()-1).length() - 1;
                    token2.set(l2, token2.get(l2).substring(0, stindex) + token2.get(l2).substring(enindex + 1));
                    incr++;
                }else {
                    incr++;
                }
                if(incr>0) { l2++;}
            }
            l2=0;
            if(incr>0) { l1++;}

        }



        matchcount += similar.stream()
                .mapToInt(String::length)
                .sum();

        matchcount += similar1.stream()
                .filter(s -> !s.isEmpty())
                .mapToInt(String::length)
                .sum();
        matchcount += similar2.stream()
                .filter(s -> !s.isEmpty())
                .mapToInt(String::length)
                .sum();



        // Remove elements from token1 that are empty or in the similar list
        token1.removeIf(s -> s.isEmpty() || similar.contains(s));

        // Remove elements from token2 that are empty or in the similar list
        token2.removeIf(s -> s.isEmpty() || similar.contains(s));

        // compare between string 1 and string2
        if(token2.size()> token1.size()) {
            token= new ArrayList<String>(token2);
//     // Calculate matching characters from name1 in name2
            while( token1.size()>=token.size() ) {
                for (int i=0;i<token1.size();i++) {
                    matchcount += GetMatching(token1.get(i));
                }
                if(token.isEmpty()==true||token1.size()==1)
                    break;
            }

        } else
        {
            // compare between string 2 and string1
            token= new ArrayList<String>(token1);
//     // Calculate matching characters from name1 in name2
            while( token2.size()>=token.size() ) {
                for (int i=0;i<token2.size();i++) {
                    matchcount += GetMatching(token2.get(i));
                }
                if(token.isEmpty()==true||token1.size()==1)
                    break;
            }
        }

        // Calculate the matching percentage for both names
        double matchingper1 = (matchcount / totalcount1) * 100;
        double matchingper2 = (matchcount / totalcount2) * 100;

        if(Double.isNaN(matchingper1)) {
            matchingper1=0;
        }
        if(Double.isNaN(matchingper2)) {
            matchingper2=0;
        }
        double Avg=(matchingper1+matchingper2)/2;

        return Avg;
//     System.out.println("Final matching score between  "+nametest1+" and  "+nametest2+" :" +matchingper1+"    "+matchingper2+"  "+Avg);
    }


    // Method to find matching characters between two strings
    public static int GetMatching(String str1) {

        int	matchingCount=0;
        if(str1.isEmpty() || token.isEmpty()) {
            matchingCount=0;
        }  else {
            double sim=0;int m=0;double v=0;
            for (int l=0;l<token.size();l++) {
                v=	FuzzySearch.weightedRatio(str1,token.get(l))/1e2;
                if(sim<v) {
                    sim=v;m=l;
                }	}
            String str2=token.get(m);
            token.remove(m);
            if (str1.length() > str2.length()) {
                String temp = str1;
                str1 = str2;
                str2 = temp;
            }

            List<Character> charList = str1.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            int index1 = 0;
            int index2 = 2;
            String st = "";
            // Loop through str1 and compare substrings of length 2 with str2
            while (index2 <= str1.length()) {

                st = str1.substring(index1, index2);
                // Check if str2 contains the substring from str1
                if (str2.contains(st)) {
                    charList.set(index1, '1');
                    charList.set(index1+1,'1');

                    index1 += 1; // Move index1 by 2 positions
                    index2 = index1 + 2; // Adjust index2 to be 2 positions ahead of index1

                } else {

                    index1++; // Move index1 by 1 position
                    index2++; // Move index2 by 1 position
                }
            }

            matchingCount = (int) charList.stream()
                    .filter(c -> c == '1')
                    .count();

//            System.out.println("all :"+charList+" "+matchingCount);
        }
        return matchingCount;
    }
    private static double findlength(String str) {
        return str.replaceAll("\\s+", "").length();
    }
    public static String removeDuplicateWords(String str) {
        str=str.toLowerCase();
        str=str.replaceAll("[^A-Za-z0-9\\s]", " ");
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
            tokens = new String[] { str };
        }
        return new ArrayList<>(Arrays.asList(tokens));
    }
}
