package com.ponsun.san.algorithm.service;

import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.*;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.fuzzyWR.FuzzyWR;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.jarowinkler.JaroWinkler;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.oneside.Oneside;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.soundexJW.SoundexJW;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.twoside.Twoside;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class IndividualRemittanceMatchesBatchProcessor {
    public static double[] IndividualRemittanceMatches(String id1,String id2,String country1,String country2,
                                                       String filter1,List<String> token1,String filter2,List<String> token2,
                                                       Map<String, List<String>> out,
                                                       String  name1_withoutsuf,List<String> token1_withoutsuf
    )  throws Exception {

        double  similarity = IDMatch.main(id1, id2);
        Soundex soundex = new Soundex();
        JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();

        double[] score=new double[6];
        score[0] = similarity;
        if(similarity!=100) {

            double weight=0.8;

//            name1 = Preprocess.datafilter_Init(name1);
//            String  name1_withoutsuf    = SuffixProcess.RemoveSuff_ind(name1);

//            name2 =  Preprocess.datafilter_Init(name2);
//            System.out.println("name1: "+name1+"|| name2:  "+name2+"|| suffix remove : "+name1_withoutsuf);

            // Tokenize the names by spaces
//            List<String> token1 = Functions.tokenizeString_Array(name1);
//            List<String> token1_withoutsuf = Functions.tokenizeString_Array(name1_withoutsuf);
//            List<String> token2 = Functions.tokenizeString_Array(name2);

            double totalcount1 = Preprocess.findlength(filter1);
            double totalcount1_withoutsuf = Preprocess.findlength(name1_withoutsuf);
            double totalcount2 = Preprocess.findlength(filter2);

            //double weight_country = CountryCorporate.main(country1, country2);

//            Map<String, List<String>> out= Functions.similarfinding( token1,token2);
            Map<String, List<String>>out_withoutsuf = Functions.similarfinding( token1_withoutsuf,token2);

            double name_one     = Oneside.main(out.get("token1"),out.get("token2"),weight);
            double name_one_    = Oneside.main(out_withoutsuf.get("token1"),out_withoutsuf.get("token2"),weight);

            double name_two = Twoside.main(out.get("token1"),out.get("token2"),out.get("similar"),out.get("similar1"),
                    out.get("similar2"),weight,totalcount1,totalcount2);
            double name_two_ = Twoside.main(out_withoutsuf.get("token1"),out_withoutsuf.get("token2"),
                    out_withoutsuf.get("similar"),out_withoutsuf.get("similar1"),
                    out_withoutsuf.get("similar2"),weight,totalcount1_withoutsuf,totalcount2);

            double name_sou  = SoundexJW.main(filter1, filter2,weight,soundex,jaroWinkler);
            double name_sou_ = SoundexJW.main(name1_withoutsuf, filter2,weight,soundex,jaroWinkler);

            double name_jw  = JaroWinkler.main(filter1,filter2,token1, token2,weight,jaroWinkler);
            double name_jw_= JaroWinkler.main(name1_withoutsuf,filter2,token1_withoutsuf, token2,weight,jaroWinkler);

            double name_f   = FuzzyWR.main(filter1,filter2,token1, token2,weight);
            double name_f_ = FuzzyWR.main(name1_withoutsuf,filter2,token1_withoutsuf, token2,weight);

            if(name_sou_>name_sou)
                name_sou=name_sou_;

            if(name_jw_>name_jw)
                name_jw=name_jw_;

            if(name_one_>name_one)
                name_one=name_one_;

            if(name_two_>name_two)
                name_two=name_two_;

            if(name_f_>name_f)
                name_f=name_f_;


//            score[1]    = name_one;
//            score[2]    = name_two;
//            score[3]    = name_sou;
//            score[4]    = name_jw;
//            score[5]    = name_f;
//            score[6]    = weight_country;
//            score[7]    = weight_dob;
//
//            for (int i = 1; i < score.length; i++) {
//                score[i] = Math.round(score[i]);
//            }
//
//            score[8]    = weight(score[1]);//1
//            score[9]    = weight(score[2]);//2
//            score[10]   = weight(score[3]);//sound
//            score[11]   = weight(score[4]);//jw
//            score[12]   = weight(score[5]);//fz

            // Assign weights to the score array
            double[] weights = {name_one, name_two, name_sou, name_jw, name_f};
            System.arraycopy(weights, 0, score, 1, weights.length);

            for (int i = 1; i < score.length; i++) {
                score[i] = Math.round(score[i]);
            }

            double weight_country = (Arrays.stream(score, 1, score.length).max().orElse(0) >= 60)
                    ? CountryCorporate.main(country1, country2)
                    : 0;

            for (int i = 1; i < score.length; i++) {
                score[i] = weight(score[i]) + weight_country;
            }
        }

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
