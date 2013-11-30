package com.mf.vistascraper.core.impl;

import com.mf.vistascraper.core.IScraperService;
import com.mf.vistascraper.model.AbstractBusinessEntity;
import com.mf.vistascraper.model.BusinessEntity;
import com.mf.vistascraper.spreadsheet.DataWriter;
import com.mf.vistascraper.util.Constants;
import com.mf.vistascraper.util.ScraperUtil;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/17/13
 * Time: 8:14 AM
 * To change this template use File | Settings | File Templates.
 */

public class VistaScraperService implements IScraperService {

    private static Logger logger = Logger.getLogger(VistaScraperService.class);

    private List<String> statesList;
    private List<String> topicsList;

    private DataWriter dataWriter;

    public VistaScraperService() {
        this.statesList = null;
        this.topicsList = null;
    }

    public void setTopicsList(List<String> topicList) {
        this.topicsList = topicList;
    }

    public void setStatesList(List<String> statesList) {
        this.statesList = statesList;
    }

    public void setDataWriter(DataWriter dataWriter) {
        this.dataWriter = dataWriter;
    }

    public void scrape() {

        String what, where, url;
        Elements businessEntityList;
        Document document, resultPageDoc;
        int pageCount;

        dataWriter.addHeader();

        for (int i=0; i<statesList.size(); i++) {
            for (int j=0; j<topicsList.size(); j++) {

                url = String.format(Constants.VISTAPRINT_URL_TEMPLATE,
                        statesList.get(i), topicsList.get(j));

                what = topicsList.get(j);
                where = statesList.get(i);
                logger.info("Search: ".concat(what).concat(" : ").concat(where));

                document = ScraperUtil.loadHTMLDocument(url, what, where, null);
                pageCount = ScraperUtil.extractPageCount(document);

                for (int k=0; k<pageCount; k++) {

                    resultPageDoc = ScraperUtil.loadHTMLDocument(url, what, where, Integer.toString(k));
                    businessEntityList = ScraperUtil.getBusinessEntityList(resultPageDoc);

                    for (Element el: businessEntityList) {

                        AbstractBusinessEntity businessEntity = ScraperUtil.extractBusinessEntity(el);
                        if (DataValidator.validate(businessEntity))
                            dataWriter.offer((BusinessEntity) businessEntity);
                    }
                }
            }
        }

        dataWriter.save();
    }
}
