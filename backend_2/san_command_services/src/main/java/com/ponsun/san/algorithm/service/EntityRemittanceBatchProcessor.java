package com.ponsun.san.algorithm.service;

import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.CountryCorporate;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.Functions;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.IDMatch;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.Preprocess;
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

public class EntityRemittanceBatchProcessor {
    public static double[]  EntityRemittanceMatches(String name1,String name2,String id1,String id2,String country1,String country2,
                                                    String filter1,List<String> token1,String filter2,List<String> token2,Map<String, List<String>> out) throws Exception {

//        String name1 = "Hanan Shipping", name2 = "gnippihsnanah LLC";
//        String country1 = "india", country2 = "india";
//        String id1="ID1930 ID1930" ,  id2="1D1493O";

        double  similarity = IDMatch.main(id1, id2);
        Soundex soundex = new Soundex();
        JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();

        double[] score=new double[6];
        score[0]=similarity;//id matching
        if(similarity!=100) {

            double weight=0.8;

//            name1 = Preprocess.datafilter_Init(name1);
//            String filter2 =  Preprocess.datafilter_Init(name2);

            // Tokenize the names by spaces
//            List<String> token1     = Functions.tokenizeString_Array(name1);
//            List<String> token2     = Functions.tokenizeString_Array(filter2);

            double totalcount1      = Preprocess.findlength(filter1);
            double totalcount2      = Preprocess.findlength(filter2);


            double name_one = Oneside.main(out.get("token1"),out.get("token2"),weight);
            double name_two = Twoside.main(out.get("token1"),out.get("token2"),out.get("similar"),out.get("similar1"),
                    out.get("similar2"),weight,totalcount1,totalcount2);
            double name_sou = SoundexJW.main(filter1, filter2,weight,soundex,jaroWinkler);
            double name_jw= JaroWinkler.main(filter1,filter2,token1, token2,weight,jaroWinkler);
            double name_f = FuzzyWR.main(filter1,filter2,token1, token2,weight);



//            score[1]=name_one;
//            score[2]=name_two;
//            score[3]=name_sou;
//            score[4]=name_jw;
//            score[5]=name_f;
//
//            for (int i = 1; i < score.length; i++) {
//                score[i] = Math.round(score[i]);
//            }
//
//            score[6]=weight_country;
//            score[7]=0;//weight_dob;
//            for (int i = 1; i < score.length; i++) {
//                score[i] = Math.round(score[i]);
//            }
//            score[8]   = weight(score[1]);
//            score[9]   = weight(score[2]);
//            score[10]  = weight(score[3]);
//            score[11]  = weight(score[4]);
//            score[12]  = weight(score[5]);//Fwwr

            double[] weights = {name_one, name_two, name_sou, name_jw, name_f};
            System.arraycopy(weights, 0, score, 1, weights.length);

            for (int i = 1; i < score.length; i++) {
                score[i] = Math.round(score[i]);
            }
//            double weight_country   = CountryCorporate.main(country1, country2);
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
