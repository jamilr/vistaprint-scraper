package com.mf.vistascraper.core.concurrent;

import com.mf.vistascraper.core.impl.KeywordRanker;
import com.mf.vistascraper.core.impl.KeywordScraperService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/20/13
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */

public class ScraperTask implements Runnable {

    private static Logger logger = Logger.getLogger(ScraperTask.class);

    private KeywordScraperService service;

    public ScraperTask() {
        service = new KeywordScraperService();
    }

    public void setTopics(List<String> topics) {
        this.service.setBaseKeywordsList(topics);
    }

    public void setStates(List<String> states) {
        this.service.setStateList(states);
    }

    public void setKeywordRanker(KeywordRanker ranker) {
        this.service.setKeywordRanker(ranker);
    }

    @Override
    public void run() {

        service.scrape();
    }
}
