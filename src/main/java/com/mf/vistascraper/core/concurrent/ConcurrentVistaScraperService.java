package com.mf.vistascraper.core.concurrent;

import com.mf.vistascraper.core.impl.KeywordRanker;
import com.mf.vistascraper.spreadsheet.DataWriter;
import com.mf.vistascraper.util.Constants;
import com.mf.vistascraper.util.PropertiesUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/28/13
 * Time: 8:51 AM
 * To change this template use File | Settings | File Templates.
 */


public class ConcurrentVistaScraperService {

    private static Logger logger = Logger.getLogger(ConcurrentVistaScraperService.class);

    private List<String> states;
    private List<String> topics;

    private DataWriter dataWriter;

    private static Thread scraperThreadA;
    private static Thread scraperThreadB;

    public ConcurrentVistaScraperService() {
    }

    public void setStateList(List<String> stateList) {
        this.states = stateList;
    }

    public void setTopicsList(List<String> topicList) {
        this.topics = topicList;
    }

    public void setDataWriter(DataWriter data) {
        dataWriter = data;
    }

    public void scrape() {

        int topicSize = topics.size();
        int topicListASize = Math.round(topicSize / 2);

        List<String> topicListA = topics.subList(0, topicListASize);
        List<String> topicListB = topics.subList(topicListASize, topicSize);

        VistaScraperTask vistaScraperTaskA = new VistaScraperTask();
        VistaScraperTask vistaScraperTaskB = new VistaScraperTask();

        vistaScraperTaskA.setStates(states);
        vistaScraperTaskB.setStates(states);

        vistaScraperTaskA.setTopics(topicListA);
        vistaScraperTaskB.setTopics(topicListB);

        vistaScraperTaskA.setDataWriter(dataWriter);
        vistaScraperTaskB.setDataWriter(dataWriter);

        scraperThreadA = new Thread(vistaScraperTaskA);
        scraperThreadA.setName("vista-scraper-thread-1");

        scraperThreadB = new Thread(vistaScraperTaskB);
        scraperThreadB.setName("vista-scraper-thread-2");

        init();
    }

    private void init() {

        try {

            scraperThreadA.start();
            scraperThreadB.start();

            scraperThreadA.join();
            scraperThreadB.join();

        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
        }
    }
}
