package com.mf.vistascraper.entry;

import com.mf.vistascraper.spreadsheet.DataWriter;
import com.mf.vistascraper.core.impl.VistaScraperService;
import com.mf.vistascraper.model.BusinessEntity;
import com.mf.vistascraper.util.PropertiesUtil;

import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/17/13
 * Time: 8:13 AM
 * To change this template use File | Settings | File Templates.
 */

public class StandaloneScraperEntry {

    public static void main(String[] args) {

        Properties propUtil = PropertiesUtil.getInstance();
        String csvFilePath = propUtil.getProperty("final-output-file");
        DataWriter dataWriter = new DataWriter(csvFilePath, true);

        VistaScraperService service = new VistaScraperService();
        service.setDataWriter(dataWriter);
        service.scrape();
    }

    private static void addEntityList(List<BusinessEntity> businessEntityList, DataWriter dataWriter) {

        for (BusinessEntity entity: businessEntityList)
            dataWriter.offer(entity);

        dataWriter.save();
    }
}

