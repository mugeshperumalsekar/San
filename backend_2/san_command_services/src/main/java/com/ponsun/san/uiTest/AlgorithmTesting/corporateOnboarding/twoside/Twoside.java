package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.twoside;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import me.xdrop.fuzzywuzzy.FuzzySearch;

public class Twoside {


    public static double main(List<String> listtoken1,List<String> listtoken2,List<String> similar,List<String> similar1,
                              List<String> similar2,double weight,double totalcount1,double totalcount2) {
        List<String> token1 = new ArrayList<>(listtoken1);
        List<String> token2 = new ArrayList<>(listtoken2);
        double matchcount = 0;

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
        if(token1.size()!=0 && token2.size()!=0 ) {
            if(token2.size()> token1.size()) {

                List<String>  token= token2;

//	    // Calculate matching characters from name1 in name2
                while(token.size()!=0) {
                    int i=0;
                    while(token1.size()>0) {
                        Map<String, Object> result= GetMatching(token1.get(i),token);
                        matchcount +=Integer.parseInt(result.get("matchingCount").toString());
                        token=(List<String>) result.get("token");
                        token1.remove(i);
                    }
                    if(token.isEmpty()==true||token1.isEmpty())
                        break;
                }

            } else    {
                // compare between string 2 and string1
                List<String>  token= token1;

//	     // Calculate matching characters from name1 in name2
                while(token.size()!=0) {
                    int i=0;
                    while(token2.size()>0) {
                        Map<String, Object> result   = GetMatching(token2.get(i),token);
                        matchcount +=Integer.parseInt(result.get("matchingCount").toString());
                        token=(List<String>) result.get("token");
                        token2.remove(i);

                    }
                    if(token.isEmpty()==true||token2.isEmpty())
                        break;
                }
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

//        double score=(Avg/100)*weight;
        return Avg;
    }

    // Method to find matching characters between two strings
    public static Map<String, Object> GetMatching(String str1, List<String> token) {
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
                String rever_St = new StringBuilder(st).reverse().toString();
                // Check if str2 contains the substring from str1
                if (str2.contains(st)|| str2.contains(rever_St)) {
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

        }
        return Map.of("matchingCount", matchingCount, "token", token);
    }
    }