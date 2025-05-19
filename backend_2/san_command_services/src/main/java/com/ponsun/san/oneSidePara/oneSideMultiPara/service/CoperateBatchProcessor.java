//package com.ponsun.san.oneSidePara.oneSideMultiPara.service;
//
//import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.*;
//import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.fuzzyWR.FuzzyWR;
//import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.jarowinkler.JaroWinkler;
//import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.oneside.Oneside;
//import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.soundexJW.SoundexJW;
//import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.twoside.Twoside;
//import com.ponsun.san.uiTest.AlgorithmTesting.parttwo.IDMatch;
//import org.apache.commons.codec.language.Soundex;
//import org.apache.commons.text.similarity.JaroWinklerSimilarity;
//
//import java.util.*;
//
//public class CoperateBatchProcessor {
//
//    public static double[] processOnBoardBatch(String name1, String name2, String id1, String id2,
//                                               String country1, String country2, String dob1, String dob2) throws Exception {
//
//        // Initialize score array
//        double[] score = new double[8];
//
//        // Step 1: ID Matching
//        score[0] = calculateIDMatch(id1, id2);
//
//        // Step 2: Initialize soundex and jaroWinkler for name similarity calculation
//        Soundex soundex = new Soundex();
//        JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();
//
//        // Step 3: Preprocess and remove suffixes from names
////        List<String> suffixes = SuffixProcess.ReadSuff();
//        ArrayList<String> suffixes =SuffixProcess.ReadSuff();
//        name1 = Preprocess.datafilter_Init(name1);
//        String name1WithoutSuffix = SuffixProcess.RemoveSuff(suffixes, name1);
//
//        name2 = Preprocess.datafilter_Init(name2);
//
//        // Tokenization of names
//        List<String> token1 = Functions.tokenizeString_Array(name1);
//        List<String> token1WithoutSuffix = Functions.tokenizeString_Array(name1WithoutSuffix);
//        List<String> token2 = Functions.tokenizeString_Array(name2);
//
//        double totalcount1 = Preprocess.findlength(name1);
//        double totalcount1_withoutsuf = Preprocess.findlength(name1WithoutSuffix);
//        double totalcount2 = Preprocess.findlength(name2);
//
//        // Calculate name matching scores
//        score[1] = calculateNameSimilarity(token1, token2, token1WithoutSuffix, soundex, jaroWinkler
//        ,totalcount1,totalcount1_withoutsuf,totalcount2);
//
//        // Step 4: Country and DOB matching
//        score[6] = Country.main(country1, country2);
//        score[7] = 0.00; // You can implement DOB matching as needed
//
//        return score;
//    }
//
//    private static double calculateIDMatch(String id1, String id2) {
//        return IDMatch.main(id1, id2);
//    }
//
//    private static double calculateNameSimilarity(List<String> token1, List<String> token2,
//                                                  List<String> token1WithoutSuffix, Soundex soundex,
//                                                  JaroWinklerSimilarity jaroWinkler,
//    double totalcount1,double totalcount1_withoutsuf,double totalcount2) {
//
//        double weight = 80;
//
//        // Token-based calculations for two different name versions
//        double weightNameTwo = calculateTwosideSimilarity(token1, token2, token1WithoutSuffix, weight,totalcount1,totalcount2,totalcount1_withoutsuf);
//        double weightNameOne = calculateOnesideSimilarity(token1, token2, token1WithoutSuffix, weight);
//        double weightNameJw = calculateJaroWinklerSimilarity(token1, token2, token1WithoutSuffix, weight, jaroWinkler);
//        double weightNameSou = calculateSoundexJWSimilarity(token1, token2, token1WithoutSuffix, weight, soundex, jaroWinkler);
//        double weightNameF = calculateFuzzyWRSimilarity(token1, token2, token1WithoutSuffix, weight);
//
//        // Return the best score from various similarity checks
//        return Math.max(Math.max(weightNameTwo, weightNameOne),
//                Math.max(Math.max(weightNameJw, weightNameSou), weightNameF));
//    }
//
//
//    private static double calculateTwosideSimilarity(List<String> token1, List<String> token2,
//                                                     List<String> token1WithoutSuffix, double weight,
//                                                     double totalcount1,double totalcount2,double totalcount1_withoutsuf) {
//
//        Map<String, List<String>> out = Functions.similarfinding(token1, token2);
//        Map<String, List<String>> outWithoutSuffix = Functions.similarfinding(token1WithoutSuffix, token2);
//
//        double weightNameTwo = Twoside.main(out.get("token1"), out.get("token2"), out.get("similar"),
//                out.get("similar1"), out.get("similar2"), weight, totalcount1, totalcount2);
//        double weightNameTwoWithoutSuffix = Twoside.main(outWithoutSuffix.get("token1"), outWithoutSuffix.get("token2"),
//                outWithoutSuffix.get("similar"), outWithoutSuffix.get("similar1"), outWithoutSuffix.get("similar2"), weight,
//                totalcount1_withoutsuf, totalcount2);
//
//        return Math.max(weightNameTwo, weightNameTwoWithoutSuffix);
//    }
//
//    private static double calculateOnesideSimilarity(List<String> token1, List<String> token2,
//                                                     List<String> token1WithoutSuffix, double weight) {
//        double weightNameOne = Oneside.main(Functions.similarfinding(token1, token2).get("token1"),
//                Functions.similarfinding(token1, token2).get("token2"), weight);
//        double weightNameOneWithoutSuffix = Oneside.main(Functions.similarfinding(token1WithoutSuffix, token2).get("token1"),
//                Functions.similarfinding(token1WithoutSuffix, token2).get("token2"), weight);
//
//        return Math.max(weightNameOne, weightNameOneWithoutSuffix);
//    }
//
//    private static double calculateJaroWinklerSimilarity(List<String> token1, List<String> token2,
//                                                         List<String> token1WithoutSuffix, double weight,
//                                                         JaroWinklerSimilarity jaroWinkler) {
//        double weightNameJw = JaroWinkler.main(token1, token2, weight, jaroWinkler);
//        double weightNameJwWithoutSuffix = JaroWinkler.main(token1WithoutSuffix, token2, weight, jaroWinkler);
//
//        return Math.max(weightNameJw, weightNameJwWithoutSuffix);
//    }
//
//    private static double calculateSoundexJWSimilarity(List<String> token1, List<String> token2,
//                                                       List<String> token1WithoutSuffix, double weight,
//                                                       Soundex soundex, JaroWinklerSimilarity jaroWinkler) {
//        double weightNameSou = SoundexJW.main(token1, token2, weight, soundex, jaroWinkler);
//        double weightNameSouWithoutSuffix = SoundexJW.main(token1WithoutSuffix, token2, weight, soundex, jaroWinkler);
//
//        return Math.max(weightNameSou, weightNameSouWithoutSuffix);
//    }
//
//    private static double calculateFuzzyWRSimilarity(List<String> token1, List<String> token2,
//                                                     List<String> token1WithoutSuffix, double weight) {
//        double weightNameF = FuzzyWR.main(token1, token2, weight);
//        double weightNameFWithoutSuffix = FuzzyWR.main(token1WithoutSuffix, token2, weight);
//
//        return Math.max(weightNameF, weightNameFWithoutSuffix);
//    }
//}

package com.ponsun.san.oneSidePara.oneSideMultiPara.service;

import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.*;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.fuzzyWR.FuzzyWR;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.jarowinkler.JaroWinkler;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.oneside.Oneside;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.soundexJW.SoundexJW;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.twoside.Twoside;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;

import java.util.*;
import java.util.LinkedHashSet;
public class CoperateBatchProcessor {
    public static double[] processOnBoardBatch(String name1, String name2, String id1, String id2,
                                             String country1, String country2, String dob1, String dob2) throws Exception {

        double[] score=new double[18];

        double  similarity = IDMatch.main(id1, id2);

        score[0] = similarity;

        Soundex soundex = new Soundex();
        JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();
        LinkedHashSet<String> suffixes =SuffixProcess.ReadSuff();

        if(similarity!=100) {

            double weight=80;

            name1 = Preprocess.datafilter_Init(name1);
            String  name1_withoutsuf= SuffixProcess.RemoveSuff(suffixes,name1);

            name2 =  Preprocess.datafilter_Init(name2);
//// System.out.println(name1+"  "+name2+" "+name1_withoutsuf);

            // Tokenize the names by spaces
            List<String> token1 = Functions.tokenizeString_Array(name1);
            List<String> token1_withoutsuf = Functions.tokenizeString_Array(name1_withoutsuf);
            List<String> token2 = Functions.tokenizeString_Array(name2);

//    // System.out.println(token1+"  "+token1_withoutsuf+" "+token2);


            double totalcount1 = Preprocess.findlength(name1);
            double totalcount1_withoutsuf = Preprocess.findlength(name1_withoutsuf);
            double totalcount2 = Preprocess.findlength(name2);

            LinkedHashSet<String> d1 = Functions.dateTokenize(dob1);
            List<Map<String, List<String>>> mapList1 = new ArrayList<>();

            // Parse all dates for both input1 and input2 once
            for (String date : d1) {
                mapList1.add(DOBMatching.parseInput(date));
            }



            LinkedHashSet<String> d2 = Functions.dateTokenize(dob2);
            List<Map<String, List<String>>> mapList2 = new ArrayList<>();


            for (String date : d2) {
                mapList2.add(DOBMatching.parseInput(date));
            }

            double weight_country = CountryCorporate.main(country1, country2);
            double weight_dob = 0.00;//DOBMatching.main(mapList1, mapList2);

            score[6]    = weight_country;
            score[7]    = weight_dob;

            Map<String, List<String>>out= Functions.similarfinding( token1,token2);

            Map<String, List<String>>out_withoutsuf= Functions.similarfinding( token1_withoutsuf,token2);

            double weight_name_two = Twoside.main(out.get("token1"),out.get("token2"),out.get("similar"),out.get("similar1"),
                    out.get("similar2"),weight,totalcount1,totalcount2);
            double weight_name_two_ = Twoside.main(out_withoutsuf.get("token1"),out_withoutsuf.get("token2"),
                    out_withoutsuf.get("similar"),out_withoutsuf.get("similar1"),
                    out_withoutsuf.get("similar2"),weight,totalcount1_withoutsuf,totalcount2);

            if(weight_name_two_>weight_name_two)
                weight_name_two=weight_name_two_;


            double weight_name_one = Oneside.main(out.get("token1"),out.get("token2"),weight);
            double weight_name_one_ = Oneside.main(out_withoutsuf.get("token1"),out_withoutsuf.get("token2"),weight);
//
            if(weight_name_one_>weight_name_one)
                weight_name_one=weight_name_one_;



            double weight_name_jw= JaroWinkler.main(name1,name2,token1, token2,weight,jaroWinkler);
            double weight_name_jw_= JaroWinkler.main(name1,name2,token1_withoutsuf, token2,weight,jaroWinkler);
            if(weight_name_jw_>weight_name_jw)
                weight_name_jw=weight_name_jw_;


            double weight_name_sou = SoundexJW.main(name1,name2,weight,soundex,jaroWinkler);
            double weight_name_sou_ = SoundexJW.main(name1, name2,weight,soundex,jaroWinkler);

            if(weight_name_sou_>weight_name_sou)
                weight_name_sou=weight_name_sou_;


            double weight_name_f = FuzzyWR.main(name1,name2,token1, token2,weight);
            double weight_name_f_ = FuzzyWR.main(name1,name2,token1_withoutsuf, token2,weight);

            if(weight_name_f_>weight_name_f)
                weight_name_f=weight_name_f_;

            score[1]    = weight_name_one;
            score[2]    = weight_name_two;
            score[3]    = weight_name_sou;
            score[4]    = weight_name_jw;
            score[5]    = weight_name_f;

            for (int i = 1; i < score.length; i++) {
                score[i] = Math.round(score[i]);
            }

            score[8] = weight(score[1]);//1
            score[9] = weight(score[2]);//2
            score[10] = weight(score[3]);//sound
            score[11] = weight(score[4]);//jw
            score[12] =  weight(score[5]);//fz

//            for (int i = 13; i < 18; i++) {
//                score[i] = 0;
//            }
        }

//        // System.out.println("two:"+score[0]+" one:  "+score[1]+"  jw:  "+score[2]+"  sou:  "+score[3]+"  fuzz:   "+score[4]+" ");
//        // System.out.println("two:"+score[9]+" one:  "+score[8]+"  jw:  "+score[11]+"  sou:  "+score[10]+"  fuzz:   "+score[12]+" ");

//        return Arrays.stream(score).max().orElse(Double.NaN)+"";
        return score;
    }

    private static double weight(double value) {
        double weightage = 0;
        if(value>=60)
            weightage=Functions.percentage(value);
        else
            weightage=value;
        return weightage;
    }
}


