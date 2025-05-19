package com.ponsun.san.algorithm.testingservice;

import com.ponsun.san.oneSidePara.arabicSan.data.ArabicSanData;
import com.ponsun.san.oneSidePara.oneSideMultiPara.data.MultiParaSearchData;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.IDMatch;
import com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding.MatchingUtility;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ScoringService {
    private MatchingUtility matchingUtils;
    Soundex soundex = new Soundex();
    JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();

        public double[] calculateScores(MultiParaSearchData multiParaSearchData, ArabicSanData batchData, String filter1, List<String> token1, String filter2, List<String> token2, Map<String, List<String>> out) {
            // Perform the scoring logic as per your business rules.
            double[] scores = new double[6];

            double idMatchingScore = IDMatch.main(multiParaSearchData.getId(), batchData.getIdValue());
            scores[0] = idMatchingScore;

            if (idMatchingScore != 100) {
                scores[1] = safeCalculate(() -> matchingUtils.OnesideMatch(out), "oneside", multiParaSearchData.getName(), batchData.getName());
                scores[2] = safeCalculate(() -> matchingUtils.TwoSideMatch(filter1, filter2, out), "exactMatch", multiParaSearchData.getName(), batchData.getName());
                scores[3] = safeCalculate(() -> matchingUtils.Soundxjw(filter1, filter2,soundex,jaroWinkler), "soundxJarowinkler_Match", multiParaSearchData.getName(), batchData.getName());
                scores[4] = safeCalculate(() -> matchingUtils.JaroMatch(filter1, filter2, token1, token2,jaroWinkler), "Jarowinkler_Match", multiParaSearchData.getName(), batchData.getName());
                scores[5] = safeCalculate(() -> matchingUtils.Fwwr(filter1, filter2, token1, token2), "Fuzzy_WeightedRatio", multiParaSearchData.getName(), batchData.getName());
            } else {
                Arrays.fill(scores, 0);  // If ID match is 100, reset all scores to 0.
            }

            return scores;
        }

        private double safeCalculate(Supplier<Double> calculation, String type, String queryCustomer, String str2) {
            try {
                Double result = calculation.get();
                return result != null ? result : 0;
            } catch (Exception e) {
                // Log exception and return default value
                System.err.println("Error during " + type + " calculation: " + e);
                return 0;
            }
        }


    @FunctionalInterface
    interface Supplier<T> {
        T get() throws Exception;
    }
}
