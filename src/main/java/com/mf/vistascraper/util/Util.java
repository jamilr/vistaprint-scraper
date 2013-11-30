package com.mf.vistascraper.util;

import org.apache.commons.validator.UrlValidator;
import org.apache.lucene.search.spell.NGramDistance;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */

public class Util {

    public static void loadData(String filePath, List<String> collection) {

        try {

            BufferedReader bufferedReader = new BufferedReader( new FileReader(new File(filePath)));

            String keyword;
            while ((keyword = bufferedReader.readLine()) != null) {
                if (keyword != null)
                    collection.add(keyword);
            }

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    class ValueComparator implements Comparator<String> {

        Map<String, Double> base;
        public ValueComparator(Map<String, Double> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with equals.
        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }

    public static int covertStringToInt(String intStr) {
        return Integer.parseInt(intStr);
    }

    public static String extractURLFromStr(String strValue) {

        String regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(strValue);

        String url = "";
        if (m.find())
            url = m.group();

        return url;
    }

    public static String extractNumFromStr(String strValue) {

        String regex = "\\(\\d+\\)\\s\\d+\\s\\-\\s\\d+";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(strValue);

        String url = "";
        if (m.find())
            url = m.group();

        return url;
    }

    public static boolean validPhone(String num) {
        return num.matches("\\(\\d+\\)\\s\\d+\\s\\-\\s\\d+");
    }

    public static boolean validURL(String urlString) {

        boolean valid;

        UrlValidator validator = new UrlValidator();
        valid = validator.isValid(urlString);

        return valid;
    }

    public static String removeNonAlphaSymbols(String source) {

        source = source.replaceAll("^[^a-zA-Z]+", "");
        source = source.replaceAll("[^a-zA-Z]+$", "");

        for (String key: Constants.SPECIAL_CHAR_MAP.keySet())
            source = source.replaceAll(key, Constants.SPECIAL_CHAR_MAP.get(key));
        return source;
    }

    public static float getWordsDistance(String wordA, String wordB) {
        NGramDistance distance = new NGramDistance();
        return distance.getDistance(wordA, wordB);
    }
}
