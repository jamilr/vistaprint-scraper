package com.mf.vistascraper.core.concurrent;

import com.mf.vistascraper.core.impl.KeywordRanker;
import com.mf.vistascraper.util.Constants;
import com.mf.vistascraper.util.PropertiesUtil;
import com.mf.vistascraper.util.Util;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/20/13
 * Time: 6:17 PM
 * To change this template use File | Settings | File Templates.
 */


public class ConcurrentKeywordScraperService {

    private static Logger logger = Logger.getLogger(ConcurrentKeywordScraperService.class);

    private static String luceneIndexDir;
    private static String statesFilePath;
    private static String baseKeywordsFilePath;
    private static String rankedKeywordsFilePath;

    private List<String> statesList;
    private List<String> baseKeywordList;

    private static Thread scraperThreadA;
    private static Thread scraperThreadB;

    private static KeywordRanker keywordRanker;

    public ConcurrentKeywordScraperService() {
    }

    public void setStateList(List<String> states) {
        statesList = states;
    }

    public void setBaseKeywordsList(List<String> baseKeywords) {
        baseKeywordList = baseKeywords;
    }

    public void setKeywordRanker(KeywordRanker ranker) {
        keywordRanker = ranker;
    }

    public void setIndexerFilePath(String luceneIndexerDirPath) {
        luceneIndexDir = luceneIndexerDirPath;
    }

    public void setRankedKeywordsFilePath(String rankedKwdsFilePath) {
        rankedKeywordsFilePath = rankedKwdsFilePath;
    }

    public void scrape() {

        keywordRanker = new KeywordRanker(luceneIndexDir);
        keywordRanker.open();

        int topicSize = baseKeywordList.size();
        int topicListASize = Math.round(topicSize / 2);

        List<String> topicListA = baseKeywordList.subList(0, topicListASize);
        List<String> topicListB = baseKeywordList.subList(topicListASize, topicSize);

        ScraperTask scraperTaskA = new ScraperTask();
        ScraperTask scraperTaskB = new ScraperTask();

        scraperTaskA.setStates(Constants.STATES);
        scraperTaskB.setStates(Constants.STATES);

        scraperTaskA.setTopics(topicListA);
        scraperTaskB.setTopics(topicListB);

        scraperTaskA.setKeywordRanker(keywordRanker);
        scraperTaskB.setKeywordRanker(keywordRanker);

        scraperThreadA = new Thread(scraperTaskA);
        scraperThreadA.setName("scraper-thread-1");

        scraperThreadB = new Thread(scraperTaskB);
        scraperThreadB.setName("scraper-thread-2");

        init();
    }

    public String[] getRankedTermList() {
        Integer topK = Integer.parseInt(PropertiesUtil.getInstance().getProperty("top_k_count"));
        return keywordRanker.rankKeywords(topK);
    }

    private void init() {

        try {

            scraperThreadA.start();
            scraperThreadB.start();

            scraperThreadA.join();
            scraperThreadB.join();

            keywordRanker.close();
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
        }
    }


}
