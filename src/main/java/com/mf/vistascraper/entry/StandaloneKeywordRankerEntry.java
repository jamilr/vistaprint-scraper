package com.mf.vistascraper.entry;

import com.mf.vistascraper.core.concurrent.ConcurrentKeywordScraperService;
import com.mf.vistascraper.core.impl.KeywordRanker;
import com.mf.vistascraper.util.Constants;
import com.mf.vistascraper.util.PropertiesUtil;
import com.mf.vistascraper.util.Util;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */

public class StandaloneKeywordRankerEntry {

    private static String luceneIndexDir;
    private static String statesFilePath;
    private static String baseKeywordsFilePath;
    private static String rankedKeywordsFilePath;

    private static ConcurrentKeywordScraperService keywordScraper;


    public static void main(String[] args) {

        init();
        scrape();
        saveRankedTermList(keywordScraper.getRankedTermList());
    }

    private static void init() {

        Properties prop = PropertiesUtil.getInstance();

        luceneIndexDir = prop.getProperty("lucene-index-dir");
        statesFilePath = prop.getProperty("states-file");
        baseKeywordsFilePath = prop.getProperty("base-keywords-file");
        rankedKeywordsFilePath = prop.getProperty("ranked-keywords-file");

        Util.loadData(statesFilePath,       Constants.STATES);
        Util.loadData(baseKeywordsFilePath, Constants.BASETOPICS);
    }

    private static void scrape() {

        KeywordRanker ranker = new KeywordRanker(luceneIndexDir);

        keywordScraper = new ConcurrentKeywordScraperService();
        keywordScraper.setStateList(Constants.STATES);
        keywordScraper.setBaseKeywordsList(Constants.BASETOPICS);
        keywordScraper.setKeywordRanker(ranker);
        keywordScraper.scrape();
    }

    private static void saveRankedTermList(String[] termArray) {

        try {

            BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter( new File(rankedKeywordsFilePath)));
            for (String term: termArray) {
                bufferedWriter.write(term);
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
