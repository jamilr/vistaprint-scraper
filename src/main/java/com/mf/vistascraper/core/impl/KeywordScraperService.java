package com.mf.vistascraper.core.impl;

import com.mf.vistascraper.model.TextDocumentEntity;
import com.mf.vistascraper.util.Constants;
import com.mf.vistascraper.util.PropertiesUtil;
import com.mf.vistascraper.util.ScraperUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */

public class KeywordScraperService {

    private List<String> statesList;
    private List<String> baseKeywordList;
    private KeywordRanker keywordRanker;
    private Set<String> urlSet;

    public KeywordScraperService() {
        urlSet = new HashSet<String>();
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

    public List<TextDocumentEntity> scrape() {

        keywordRanker.open();

        List<TextDocumentEntity> scrapedData = new ArrayList<TextDocumentEntity>();

        Elements businessEntityEls;
        Document resultPageDoc, document;

        String url, what, where;
        int pageCount;

        for (int i=0; i<statesList.size(); i++) {
            for (int j=0; j<baseKeywordList.size(); j++) {

                url = String.format(Constants.VISTAPRINT_URL_TEMPLATE,
                        statesList.get(i), baseKeywordList.get(j));

                what = baseKeywordList.get(j);
                where = statesList.get(i);

                document = ScraperUtil.loadHTMLDocument(url, what, where, null);
                pageCount = ScraperUtil.extractPageCount(document);

                for (int k=0; k<pageCount; k++) {

                    resultPageDoc = ScraperUtil.loadHTMLDocument(url, what, where, Integer.toString(k));
                    businessEntityEls = ScraperUtil.getBusinessEntityLink(resultPageDoc);

                    for (Element el: businessEntityEls)
                        addKeywordsToIndexer(el);
                }
            }
        }
        return scrapedData;
    }

    public String[] getRankedTermList() {
        Integer topK = Integer.parseInt(PropertiesUtil.getInstance().getProperty("top_k_count"));
        return keywordRanker.rankKeywords(topK);
    }

    private String scrapeSection(Document entityDoc, String sectionName) {

        if (entityDoc == null) return "";
        Element sectionEl = ScraperUtil.getElementWithText(entityDoc, sectionName);
        String sectionContent = (sectionEl != null)?sectionEl.text():"";
        return sectionContent;
    }

    private void addKeywordsToIndexer(Element element) {

        String sectionContent;
        String entityURL = element.attr("abs:href");

        if (!keywordRanker.offerEntity(entityURL)) return;

        Document entityDoc = ScraperUtil.loadHTMLDocumentUsingPOST(entityURL);
        for (int n=0; n< Constants.SECTIONS.size(); n++) {
            sectionContent = scrapeSection(entityDoc, Constants.SECTIONS.get(n)).trim();
            if (sectionContent != null && !sectionContent.isEmpty())
                addTextToIndexer(sectionContent);
        }
    }

    private void addTextToIndexer(String sectionContent) {
        keywordRanker.addText(sectionContent);
    }
}
