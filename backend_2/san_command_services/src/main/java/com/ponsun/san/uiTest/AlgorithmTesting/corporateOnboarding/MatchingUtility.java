package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding;

import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.fuzzyWR.*;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.jarowinkler.JWWithOutToken;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.jarowinkler.JaroWinkler;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.oneside.Oneside;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.soundexJW.SoundexFuzzyRatio;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.soundexJW.SoundexJW;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.twoside.Twoside;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;

import java.util.List;
import java.util.Map;
public class MatchingUtility {
    public static double OnesideMatch(Map<String, List<String>> out) throws Exception {
        double weight=80;
        double weight_name = Oneside.main(out.get("token1"),out.get("token2"),weight);
        double similarity = weight_name ;

        return similarity;
    }
    public static double TwoSideMatch(String name1,String name2,Map<String, List<String>>out) throws Exception {
        double weight=80;
        double totalcount1 = Preprocess.findlength(name1);
        double totalcount2 = Preprocess.findlength(name2);
        double weight_name = Twoside.main(out.get("token1"),out.get("token2"),out.get("similar"),out.get("similar1"),
                out.get("similar2"),weight,totalcount1,totalcount2);
        double similarity = weight_name ;
        return similarity;
    }
//    public static double Soundxjw(List<String> token1, List<String> token2,  Soundex soundex, JaroWinklerSimilarity jaroWinkler) {
public static double Soundxjw(String name1,String name2,  Soundex soundex, JaroWinklerSimilarity jaroWinkler) {
        double weight=80;
        double weight_name = SoundexJW.main(name1,name2,weight,soundex,jaroWinkler);
        double  similarity = weight_name ;
        return similarity;
    }
    public static double JaroMatch(String filter1,String filter2,List<String> token1,List<String> token2,JaroWinklerSimilarity jaroWinkler) throws Exception {
        double weight=80;
        double weight_name = JaroWinkler.main(filter1,filter2 ,token1, token2,weight,jaroWinkler);
        double similarity = weight_name ;
        return similarity;
    }
    public static double Fwwr(String filter1,String filter2,List<String> token1,List<String> token2) throws Exception {
        double weight=80;
        double weight_name = FuzzyWR.main(filter1,filter2,token1, token2,weight);
        double similarity = weight_name ;
        return similarity;
    }
    ///////////////////////////////////////////modified code///////////////////////////////////
    public static double Fwwrsimlarity(String filter1,String filter2,List<String> token1,List<String> token2) throws Exception {
        double weight=80;
        double weight_name = FuzzyRatioSimilarity.main(filter1,filter2,token1, token2,weight);
        double similarity = weight_name ;
        return similarity;
    }
    public static double FuzzyRatio(String filter1,String filter2,List<String> token1,List<String> token2) throws Exception {
        double weight=80;
        double weight_name = FuzzyRatio.main(filter1,filter2,token1, token2,weight);
        double similarity = weight_name ;
        return similarity;
    }
    public static double FuzzyRatioWithOutToken(String filter1,String filter2,List<String> token1,List<String> token2) throws Exception {
        double weight=80;
        double weight_name = FuzzyRatioWithOutToken.main(filter1,filter2,weight);
        double similarity = weight_name ;
        return similarity;
    }
    public static double FuzzyWRWithOutToken(String filter1,String filter2,List<String> token1,List<String> token2) throws Exception {
        double weight=80;
        double weight_name = FuzzyWRWithOutToken.main(filter1,filter2,weight);
        double similarity = weight_name ;
        return similarity;
    }
    public static double JW_withouttoken(String filter1,String filter2,List<String> token1,List<String> token2) throws Exception {
        double weight=80;
        double weight_name = JWWithOutToken.main(filter1,filter2,weight);
        double similarity = weight_name ;
        return similarity;
    }
    public static double SoundexFuzzyRatio(List<String> token1, List<String> token2,  Soundex soundex, JaroWinklerSimilarity jaroWinkler) {
        double weight=80;
        double weight_name = SoundexFuzzyRatio.main(token1, token2,weight,soundex,jaroWinkler);
        double  similarity = weight_name ;
        return similarity;
    }
}
