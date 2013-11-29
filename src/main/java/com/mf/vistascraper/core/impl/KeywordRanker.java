package com.mf.vistascraper.core.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/17/13
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */

public class KeywordRanker {

    private LuceneWriter keywordWriter;
    private TermRankAnalyzer termRankAnalyzer;
    private Set<String> urlSet;

    public KeywordRanker(String pathToIndex) {

        keywordWriter = new LuceneWriter(pathToIndex);
        termRankAnalyzer = new TermRankAnalyzer();
        termRankAnalyzer.setIndexerFilePath(pathToIndex);
        urlSet = new HashSet<String>();
    }

    public void addText(String text) {
        keywordWriter.addText(text);
    }

    public boolean offerEntity(String entityURL) {

        boolean inserted = false;
        synchronized (urlSet) {
            if (!urlSet.contains(entityURL)) {
                urlSet.add(entityURL);
                inserted = true;
            }
        }
        return inserted;
    }


    public String[] rankKeywords(int top_k) {

        List<String> termList = termRankAnalyzer.getTopRankedTerms(top_k);
        String[] termsArray = termList.toArray(new String[termList.size()]);
        return termsArray;
    }

    public void open() {

        if (!keywordWriter.isOpened())
            keywordWriter.openIndex();
    }

    public void close() {

        if (keywordWriter.isOpened())
            keywordWriter.close();
    }
}
