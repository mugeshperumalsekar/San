package com.ponsun.san.algorithm.service;

import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.*;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.fuzzyWR.FuzzyWR;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.jarowinkler.JaroWinkler;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.oneside.Oneside;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.soundexJW.SoundexJW;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.twoside.Twoside;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;

import java.util.*;
import java.util.stream.Collectors;

public class EntityOnboardingBatchProcessor {
    public static double[] EntityOnboarding( String id1, String id2,
                                               String country1, String country2, String filter1,List<String> token1,String filter2,List<String> token2,Map<String, List<String>> out
                                                ,String name1_withoutsuf,List<String> token1_withoutsuf) throws Exception {
        double[] score = new double[11];
        double similarity = IDMatch.main(id1, id2);
        score[0] = similarity;

        Soundex soundex = new Soundex();
        JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();

        if(similarity!=100) {
            double weight=80;
//            name2 =  Preprocess.datafilter_Init(name2);
//            List<String> token2 = Functions.tokenizeString_Array(name2);

            double totalcount1 = Preprocess.findlength(filter1);
            double totalcount1_withoutsuf = Preprocess.findlength(name1_withoutsuf);
            double totalcount2 = Preprocess.findlength(filter2);

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

            double weight_name_jw= JaroWinkler.main(filter1,filter2,token1, token2,weight,jaroWinkler);
            double weight_name_jw_= JaroWinkler.main(filter1,filter2,token1_withoutsuf, token2,weight,jaroWinkler);
            if(weight_name_jw_>weight_name_jw)
                weight_name_jw=weight_name_jw_;

            double weight_name_sou = SoundexJW.main(filter1,filter2,weight,soundex,jaroWinkler);
            double weight_name_sou_ = SoundexJW.main(filter1, filter2,weight,soundex,jaroWinkler);

            if(weight_name_sou_>weight_name_sou)
                weight_name_sou=weight_name_sou_;

            double weight_name_f = FuzzyWR.main(filter1,filter2,token1, token2,weight);
            double weight_name_f_ = FuzzyWR.main(filter1,filter2,token1_withoutsuf, token2,weight);

            if(weight_name_f_>weight_name_f)
                weight_name_f=weight_name_f_;

            // Assign weights to the score array
            double[] weights = {weight_name_one, weight_name_two, weight_name_sou, weight_name_jw, weight_name_f};
            System.arraycopy(weights, 0, score, 1, weights.length);

            for (int i = 1; i < score.length; i++) {
                score[i] = Math.round(score[i]);
            }

            double weight_country = (Arrays.stream(score, 1, score.length).max().orElse(0) >= 60)
                    ? CountryCorporate.main(country1, country2)
                    : 0;

            for (int i = 6; i < score.length; i++) {
                score[i] = weight(score[i]) + weight_country;
            }

        }
        return score;
        //return new double[]{Collections.max(Arrays.stream(score).boxed().collect(Collectors.toList()))};
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
