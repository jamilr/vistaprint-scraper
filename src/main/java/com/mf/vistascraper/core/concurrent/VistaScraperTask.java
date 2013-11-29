package com.mf.vistascraper.core.concurrent;

import com.mf.vistascraper.core.impl.KeywordRanker;
import com.mf.vistascraper.core.impl.KeywordScraperService;
import com.mf.vistascraper.core.impl.VistaScraperService;
import com.mf.vistascraper.spreadsheet.DataWriter;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/28/13
 * Time: 8:51 AM
 * To change this template use File | Settings | File Templates.
 */


public class VistaScraperTask implements Runnable {

    private static Logger logger = Logger.getLogger(VistaScraperTask.class);

    private VistaScraperService service;

    public VistaScraperTask() {
        service = new VistaScraperService();
    }

    public void setTopics(List<String> topics) {
        this.service.setTopicsList(topics);
    }

    public void setStates(List<String> states) {
        this.service.setStatesList(states);
    }

    public void setDataWriter(DataWriter dataWriter) {
        this.service.setDataWriter(dataWriter);
    }

    @Override
    public void run() {
        service.scrape();
    }
}
