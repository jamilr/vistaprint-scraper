package com.mf.vistascraper.entry.concurrent;

import com.mf.vistascraper.core.concurrent.ConcurrentVistaScraperService;
import com.mf.vistascraper.spreadsheet.DataWriter;
import com.mf.vistascraper.util.Constants;
import com.mf.vistascraper.util.PropertiesUtil;
import com.mf.vistascraper.util.Util;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/28/13
 * Time: 8:52 AM
 * To change this template use File | Settings | File Templates.
 */


public class ConcurrentVistaScraperServiceEntry {

    private static String statesFilePath;
    private static String rankedKeywordsFilePath;
    private static String outputFilePath;

    private static ConcurrentVistaScraperService service;

    public static void main(String[] args) {
        init();
        scrape();
    }

    private static void scrape() {

        DataWriter dataWriter = new DataWriter(outputFilePath, true);

        service = new ConcurrentVistaScraperService();
        service.setTopicsList(Constants.TOPICS);
        service.setStateList(Constants.STATES);
        service.setDataWriter(dataWriter);
        service.scrape();
    }

    private static void init() {

        Properties prop = PropertiesUtil.getInstance();

        outputFilePath = prop.getProperty("output-file-path");
        statesFilePath = prop.getProperty("states-file");
        rankedKeywordsFilePath = prop.getProperty("ranked-keywords-file");

        Util.loadData(statesFilePath, Constants.STATES);
        Util.loadData(rankedKeywordsFilePath, Constants.TOPICS);
    }
}
