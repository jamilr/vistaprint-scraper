package com.mf.vistascraper.entry;

import com.mf.vistascraper.core.impl.TermRankAnalyzer;
import com.mf.vistascraper.util.PropertiesUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jr
 * Date: 11/19/13
 * Time: 7:10 PM
 * To change this template use File | Settings | File Templates.
 */

public class StandaloneTermRankAnalyzerEntry {

    private static TermRankAnalyzer termRankAnalyzer;

    public static void main(String[] args) {

        String indexerFilePath = PropertiesUtil.getInstance().getProperty("lucene-index-dir");
        String rankedTermsFilePath = PropertiesUtil.getInstance().getProperty("ranked-keywords-file");
        int topK = Integer.parseInt(PropertiesUtil.getInstance().getProperty("top_k_count"));

        termRankAnalyzer = new TermRankAnalyzer();
        termRankAnalyzer.setIndexerFilePath(indexerFilePath);
        List<String> topRankedTerms = termRankAnalyzer.getTopRankedTerms(topK);

        saveRankedTermList(rankedTermsFilePath, topRankedTerms);
    }

    private static void saveRankedTermList(String rankedTermsFilePath, List<String> termArray) {

        BufferedWriter bufferedWriter;
        try {

            bufferedWriter = new BufferedWriter( new FileWriter( new File(rankedTermsFilePath)));
            for (String term: termArray) {
                bufferedWriter.write(term);
                bufferedWriter.write("\n");
            }

            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
