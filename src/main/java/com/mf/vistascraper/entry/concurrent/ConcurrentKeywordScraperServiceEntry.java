package com.mf.vistascraper.entry.concurrent;

import com.mf.vistascraper.core.concurrent.ConcurrentKeywordScraperService;
import com.mf.vistascraper.core.impl.KeywordRanker;
import com.mf.vistascraper.util.Constants;
import com.mf.vistascraper.util.PropertiesUtil;
import com.mf.vistascraper.util.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/20/13
 * Time: 6:26 PM
 * To change this template use File | Settings | File Templates.
 */

public class ConcurrentKeywordScraperServiceEntry {

    private static String luceneIndexDir;
    private static String statesFilePath;
    private static String baseKeywordsFilePath;
    private static String rankedKeywordsFilePath;

    private static ConcurrentKeywordScraperService service;

    public static void main(String[] args) {

        init();
        scrape();
        saveRankedTermList(service.getRankedTermList());
    }

    private static void scrape() {

        KeywordRanker ranker = new KeywordRanker(luceneIndexDir);

        service = new ConcurrentKeywordScraperService();
        service.setIndexerFilePath(luceneIndexDir);
        service.setRankedKeywordsFilePath(rankedKeywordsFilePath);
        service.setStateList(Constants.STATES);
        service.setBaseKeywordsList(Constants.BASETOPICS);
        service.setKeywordRanker(ranker);
        service.scrape();
    }

    private static void init() {

        Properties prop = PropertiesUtil.getInstance();

        luceneIndexDir = prop.getProperty("lucene-index-dir");
        statesFilePath = prop.getProperty("states-file");
        baseKeywordsFilePath = prop.getProperty("base-keywords-file");
        rankedKeywordsFilePath = prop.getProperty("ranked-keywords-file");

        Util.loadData(statesFilePath, Constants.STATES);
        Util.loadData(baseKeywordsFilePath, Constants.BASETOPICS);
    }

    private static void saveRankedTermList(String[] termArray) {

        BufferedWriter bufferedWriter;
        try {

            bufferedWriter = new BufferedWriter( new FileWriter( new File(rankedKeywordsFilePath)));
            for (String term: termArray)
                bufferedWriter.write(term.concat("\n"));


            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
