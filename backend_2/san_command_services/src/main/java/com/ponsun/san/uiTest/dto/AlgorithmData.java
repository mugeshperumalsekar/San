package com.ponsun.san.uiTest.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AlgorithmData {
    private Double initials;
    private Double twoside;
    private Double jarowinkler;
    private Double FuzzyWuzzyWR;
    private Double SoundX_JW;
    private Double DaitchMokotoffJW;
    private Double BeiderMorseJW;

    private Double JW_n;
    private Double JWToken;
    private Double FWWR;
    private Double FWWRToken;
    private Double soundex_withouttoken_fuzzy;
    private Double Soundex_withouttoken_JW;

    public AlgorithmData(Double initials, Double twoside, Double jarowinkler, Double fuzzyWuzzyWR, Double soundX_JW, Double daitchMokotoffJW, Double beiderMorseJW, Double JW_n, Double JWToken, Double FWWR, Double FWWRToken, Double soundex_withouttoken_fuzzy,Double Soundex_withouttoken_JW) {
        this.initials = initials;
        this.twoside = twoside;
        this.jarowinkler = jarowinkler;
        this.FuzzyWuzzyWR = fuzzyWuzzyWR;
        this.SoundX_JW = soundX_JW;
        this.DaitchMokotoffJW = daitchMokotoffJW;
        this.BeiderMorseJW = beiderMorseJW;
        this.JW_n = JW_n;
        this.JWToken = JWToken;
        this.FWWR = FWWR;
        this.FWWRToken = FWWRToken;
        this.soundex_withouttoken_fuzzy= soundex_withouttoken_fuzzy;
        this.Soundex_withouttoken_JW=Soundex_withouttoken_JW;
    }

    public static AlgorithmData newInstance(Double initials, Double twoside, Double jarowinkler, Double fuzzyWuzzyWR, Double soundX_JW, Double daitchMokotoffJW, Double beiderMorseJW, Double JW_n, Double JWToken, Double FWWR, Double FWWRToken, Double soundex_withouttoken_fuzzy,Double Soundex_withouttoken_JW) {
        return new AlgorithmData( initials,  twoside,  jarowinkler,  fuzzyWuzzyWR,  soundX_JW,  daitchMokotoffJW,  beiderMorseJW, JW_n,  JWToken,  FWWR,  FWWRToken,soundex_withouttoken_fuzzy,Soundex_withouttoken_JW);
    }
}
