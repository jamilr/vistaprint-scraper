package com.mf.vistascraper.entry;

import com.mf.vistascraper.spreadsheet.DataWriter;
import com.mf.vistascraper.core.impl.VistaScraperService;
import com.mf.vistascraper.util.Constants;
import com.mf.vistascraper.util.PropertiesUtil;
import com.mf.vistascraper.util.Util;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/27/13
 * Time: 9:09 PM
 * To change this template use File | Settings | File Templates.
 */

public class StandaloneVistaPrintScraperEntry {

    private static String statesFilePath;
    private static String rankedKeywordsFilePath;
    private static String outputFilePath;

    private static VistaScraperService vistaScraper;
    private static DataWriter dataWriter;


    public static void main(String[] args) {
        init();
        scrape();
    }

    private static void init() {

        Properties prop = PropertiesUtil.getInstance();

        statesFilePath = prop.getProperty("states-file");
        rankedKeywordsFilePath = prop.getProperty("ranked-keywords-file");
        outputFilePath = prop.getProperty("output-file-path");

        Util.loadData(statesFilePath, Constants.STATES);
        Util.loadData(rankedKeywordsFilePath, Constants.TOPICS);

        dataWriter = new DataWriter(outputFilePath, true);
    }

    private static void scrape() {

        vistaScraper = new VistaScraperService();
        vistaScraper.setStatesList(Constants.STATES);
        vistaScraper.setTopicsList(Constants.TOPICS);
        vistaScraper.setDataWriter(dataWriter);
        vistaScraper.scrape();
    }
}
