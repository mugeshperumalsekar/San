package com.ponsun.san.uiTest.AlgorithmTesting.corporateOnboarding;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class DOBMatching {
    public static double main(List<Map<String, List<String>>> mapList1,List<Map<String, List<String>>> mapList2) {
        double score = 0;


        // Calculate similarity for all pairs of parsed dates
//        mapList1.parallelStream().forEach(m1 ->
//        mapList2.parallelStream().forEach(m2 ->
//            similarity.add(matching(m1, m2))
//        )
//    );
        List<Double> similarity = new ArrayList<>();

        similarity = mapList1.parallelStream()
                .flatMap(m1 -> mapList2.stream().map(m2 -> matching(m1, m2)))
                .collect(Collectors.toList());

        // Return the maximum similarity found
        score = Collections.max(similarity);

        return score;
    }

    public static double matching(Map<String, List<String>> dob1, Map<String, List<String>> dob2 ) {
        double similarity = 0;

        // Extract values from the maps
        List<String> year1 = new ArrayList<>(dob1.get("Years"));
        List<String> month1 = new ArrayList<>(dob1.get("Months"));
        List<String> day1 = new ArrayList<>(dob1.get("Days"));

        List<String> year2 = new ArrayList<>(dob2.get("Years"));
        List<String> month2 = new ArrayList<>(dob2.get("Months"));
        List<String> day2 = new ArrayList<>(dob2.get("Days"));

        // Remove empty or null values
        year1.removeIf(item -> item == null || item.trim().isEmpty());
        month1.removeIf(item -> item == null || item.trim().isEmpty());
        day1.removeIf(item -> item == null || item.trim().isEmpty());
        year2.removeIf(item -> item == null || item.trim().isEmpty());
        month2.removeIf(item -> item == null || item.trim().isEmpty());
        day2.removeIf(item -> item == null || item.trim().isEmpty());


        int yearflg=0; int monthflag=0;int dayflaag=0;
//        if(year1.size()==0||year2.size()==0||year1.isEmpty()||year2.isEmpty()) {
//        	similarity=5;
//      } else {
//    	  for (int l1=0;l1<year1.size();l1++) {
//    			  if(year2.contains(year1.get(l1))) {
//    				  yearflg=1;
//    				  similarity=10;
//    				  break;
//    		  }
//    	  }


        if (year1.isEmpty() || year2.isEmpty()) {
            similarity = 5;
        } else if (year1.stream().anyMatch(year2::contains)) {
            yearflg = 1;
            similarity = 10;
        }

        if (!month1.isEmpty() && !month2.isEmpty() && yearflg == 1) {
            monthflag = month1.stream().anyMatch(month2::contains) ? 1 : 0;
            similarity = (monthflag == 1) ? 10 : 0;
        }

        if (!day1.isEmpty() && !day2.isEmpty() && yearflg == 1 && monthflag == 1) {
            dayflaag = day1.stream().anyMatch(day2::contains) ? 1 : 0;
            similarity = (dayflaag == 1) ? 10 : 0;
        }


        return similarity;
    }

    public static Map<String, List<String>> parseInput(String input) {
        List<String> years = new ArrayList<>();
        List<String> months = new ArrayList<>();
        List<String> days = new ArrayList<>();

        input = input.trim();

        // 1974-Nov-02 format
        if (input.matches("\\d{4}-[A-Za-z]{3,}-\\d{2}")) {
            String[] parts = input.split("-");
            String year = parts[0];
            String month = getMonthNumber(parts[1]);
            String day = parts[2];

            years.add(year);
            months.add(month);
            days.add(day);
        }
        //1974-11-02
        else if(input.matches("\\d{4}-\\d{1,}-\\d{2}")) {
            String[] parts = input.split("-");
            String year = parts[0];
            String month = parts[1] != null ? String.format("%02d", Integer.parseInt(parts[1])) : "-1";
            String day = parts[2];

            years.add(year);
            months.add(month);
            days.add(day);
        }
        // 2016-Mar- format
        else if (input.matches("\\d{4}-[A-Za-z]{3,}-?")) {
            String[] parts = input.split("-");
            years.add(parts[0]);
            months.add( getMonthNumber(parts[1]));
            days.add(""); // Empty day
        }
        // 2016-03- format
        else if (input.matches("\\d{4}-(0?[1-9]|1[0-2])-?")) {
            String[] parts = input.split("-");
            years.add(parts[0]);
            String m=parts[1] != null ? String.format("%02d", Integer.parseInt(parts[1])) : "-1";
            months.add(m);
            days.add(""); // Empty day
        }
        // 2003-- format
        else if (input.matches("\\d{4}-?")) {
            String year = input.replace("-", "");
            years.add(year);
            months.add(""); // No month
            days.add(""); // No day
        }
        // Handle year ranges like "1979 to 1982" or "1979 -1982"

        else if (input.matches("\\d{4}\\s*(to|-|–)\\s*\\d{4}")) {
            String[] range = input.split("\\s*(to|-|–)\\s*");
            int startYear = Integer.parseInt(range[0]);
            int endYear = Integer.parseInt(range[1]);
            for (int year = startYear; year <= endYear; year++) {
                years.add(String.valueOf(year));
                months.add("");
                days.add("");
            }
        }
        Map<String, List<String>> result = new HashMap<>();
        result.put("Years", years);
        result.put("Months", months);
        result.put("Days", days);
        return result;
    }

    private static String getMonthNumber(String month) {
        Map<String, Month> MONTH_MAP = new HashMap<>();
        MONTH_MAP.put("JAN", Month.JANUARY);
        MONTH_MAP.put("FEB", Month.FEBRUARY);
        MONTH_MAP.put("MAR", Month.MARCH);
        MONTH_MAP.put("APR", Month.APRIL);
        MONTH_MAP.put("MAY", Month.MAY);
        MONTH_MAP.put("JUN", Month.JUNE);
        MONTH_MAP.put("JUL", Month.JULY);
        MONTH_MAP.put("AUG", Month.AUGUST);
        MONTH_MAP.put("SEP", Month.SEPTEMBER);
        MONTH_MAP.put("OCT", Month.OCTOBER);
        MONTH_MAP.put("NOV", Month.NOVEMBER);
        MONTH_MAP.put("DEC", Month.DECEMBER);
        MONTH_MAP.put("JANUARY", Month.JANUARY);
        MONTH_MAP.put("FEBRUARY", Month.FEBRUARY);
        MONTH_MAP.put("MARCH", Month.MARCH);
        MONTH_MAP.put("APRIL", Month.APRIL);
        MONTH_MAP.put("MAY", Month.MAY);
        MONTH_MAP.put("JUNE", Month.JUNE);
        MONTH_MAP.put("JULY", Month.JULY);
        MONTH_MAP.put("AUGUST", Month.AUGUST);
        MONTH_MAP.put("SEPTEMBER", Month.SEPTEMBER);
        MONTH_MAP.put("OCTOBER", Month.OCTOBER);
        MONTH_MAP.put("NOVEMBER", Month.NOVEMBER);
        MONTH_MAP.put("DECEMBER", Month.DECEMBER);
        Month result = MONTH_MAP.get(month.toUpperCase());
        return result != null ? String.format("%02d", result.getValue()) : "-1";
    }
}
